package com.ef;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ef.config.BatchConfig;
import com.ef.config.DBConfig;
import com.ef.model.Report;
import com.ef.model.Requester;
import com.ef.service.ReportService;
import com.ef.service.RequesterService;
import com.ef.utils.GeneralUtils;

import org.apache.log4j.Logger;

public class Parser {

	private static Logger logger = Logger.getLogger(Parser.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";
	private static final String DB_ZONE = "UTC";

	private static String accesslog = null;
	private static String startDate = null;
	private static String duration = null;
	private static String threshold = null;

	public static void main(String[] args) {
		logger.info("	* Begin Parser Process	*");
		long time = System.currentTimeMillis();

		try {
			String evaluateParamMsg = evaluateParameters(args);

			if (evaluateParamMsg.equals("OK")) {
				if (accesslog != null) {
					executeReadLog(accesslog);
				}

				executeEvaluateRequest(startDate, duration, threshold);
			} else {
				logger.error("	* Error: " + evaluateParamMsg + "	*");
			}
		} catch (Exception e) {
			logger.error("	* Error: " + e.getMessage() + "	*");
		}

		logger.info("	* Finish Parser Process - " + ((System.currentTimeMillis() - time) / 1000) + " seg.	*");
	}

	@SuppressWarnings("unused")
	public static void executeReadLog(String filePath) {
		logger.info("	* Begin Reading Log and Writing to DB	*");
		long time = System.currentTimeMillis();
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BatchConfig.class);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("logFileJob");

		try {
			JobParametersBuilder builder = new JobParametersBuilder();
			builder.addString("filePath", filePath);

			JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
		} catch (Exception e) {
			logger.error("	* Error: " + e.getMessage() + "	*");
		}

		context.close();

		logger.info("	* Finish Reading Log and Writing to DB - " + ((System.currentTimeMillis() - time) / 1000) + " seg.	*");
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public static void executeEvaluateRequest(String startDate, String duration, String threshold) throws Exception {
		logger.info("	* Begin Evaluating Request Report	*");
		long time = System.currentTimeMillis();

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);
		RequesterService requesterService = context.getBean(RequesterService.class);
		ReportService reportService = context.getBean(ReportService.class);

		DateTimeFormatter dateFomatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDateTime formattedStartDate = LocalDateTime.parse(startDate, dateFomatter);
		LocalDateTime formattedEndDate = formattedStartDate;
		
		// Apply the additional time to the endDate depending on the duration (hourly, daily)
		if (duration.equalsIgnoreCase("hourly")) {
			formattedEndDate = formattedStartDate.plusHours(1);
		}else if (duration.equalsIgnoreCase("daily")) {
			formattedEndDate = formattedStartDate.plusDays(1);			
		}
		
		// Save new report in database
		Report reportTemp = new Report();
		reportTemp.setReportDate(Timestamp.valueOf(GeneralUtils.changeDateZoneForDB(LocalDateTime.now(), DB_ZONE)));
		reportTemp.setStartDate(Timestamp.valueOf(GeneralUtils.changeDateZoneForDB(formattedStartDate, DB_ZONE)));
		reportTemp.setDuration(duration);;
		reportTemp.setThreshold(Integer.parseInt(threshold));
		
		reportService.add(reportTemp);
		
		// Find the IP Addresses that over pass the amount of requests compare to the threshold
		List<Requester> listRequesters = requesterService.findByOverThreshold(threshold,
				Timestamp.valueOf(formattedStartDate), Timestamp.valueOf(formattedEndDate));
		
		if (listRequesters.size() > 0) {
			logger.info("	* The next IP are 'BLOCKED' based on the evaluation:	*");

			for (int i = 0; i < listRequesters.size(); i++) {
				Requester requesterTemp = listRequesters.get(i);
				
				requesterTemp.getReportDetails().get(0).setReport(reportTemp);
								
				logger.info("	* " + (i + 1) + ". IPAddress: " + requesterTemp.getIpAddress()
						+ "	- Requests: " + requesterTemp.getReportDetails().get(0).getRequestCount() + "	*");

		        requesterService.merge(requesterTemp);
			}
		}

		context.close();

		logger.info("	* Finish Evaluating Request Report - " + ((System.currentTimeMillis() - time) / 1000) + " seg.	*");
	}

	public static String evaluateParameters(String[] parameters) {
		String missingMsg = "";

		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].contains("--accesslog=")) {
				accesslog = parameters[i].replaceAll("--accesslog=", "");
			} else if (parameters[i].contains("--startDate=")) {
				startDate = parameters[i].replaceAll("--startDate=", "");
			} else if (parameters[i].contains("--duration=")) {
				duration = parameters[i].replaceAll("--duration=", "");
			} else if (parameters[i].contains("--threshold=")) {
				threshold = parameters[i].replaceAll("--threshold=", "");
			}
		}

		if (accesslog != null) {
			if (!GeneralUtils.isValidPath(accesslog)) {
				return "The parameter accesslog must have a valid path for the log file";
			}
		}

		if (startDate != null) {
			if (!GeneralUtils.isValidDateFormat(DATE_FORMAT, startDate)) {
				return "The parameter startDate must have the following format: yyyy-MM-dd.HH:mm:ss";
			}
		} else {
			missingMsg = missingMsg + ((missingMsg == null) ? "startDate" : ", startDate");
		}

		if (duration != null) {
			if (!duration.equalsIgnoreCase("hourly") && !duration.equalsIgnoreCase("daily")) {
				return "The parameter duration must have one of the following values: hourly or daily";
			}
		} else {
			missingMsg = missingMsg + ((missingMsg == null) ? "duration" : ", duration");
		}

		if (threshold != null) {
			if (!GeneralUtils.isNumeric(threshold)) {
				return "The parameter threshold must be a numeric value with no decimals nor signs";
			}
		} else {
			missingMsg = missingMsg + ((missingMsg == null) ? "threshold" : ", threshold");
		}

		if (!missingMsg.equals("")) {
			return "Missing parameters: " + missingMsg;
		}

		return "OK";
	}
}