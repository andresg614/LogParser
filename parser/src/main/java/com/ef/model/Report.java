package com.ef.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "report_date")
	private Timestamp reportDate;

	@Column(name = "start_date")
	private Timestamp startDate;

	@Column(name = "duration")
	private String duration;

	@Column(name = "threshold")
	private Integer threshold;
	
	@OneToMany(mappedBy = "report", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ReportDetail> reportDetails;
	
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Report(Timestamp reportDate, Timestamp startDate, String duration, Integer threshold,
			List<ReportDetail> reportDetails) {
		super();
		this.reportDate = reportDate;
		this.startDate = startDate;
		this.duration = duration;
		this.threshold = threshold;
		this.reportDetails = reportDetails;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the reportDate
	 */
	public Timestamp getReportDate() {
		return reportDate;
	}

	/**
	 * @param reportDate the reportDate to set
	 */
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the threshold
	 */
	public Integer getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return the reportDetails
	 */
	public List<ReportDetail> getReportDetails() {
		return reportDetails;
	}

	/**
	 * @param reportDetails the reportDetails to set
	 */
	public void setReportDetails(List<ReportDetail> reportDetails) {
		this.reportDetails = reportDetails;
	}
}