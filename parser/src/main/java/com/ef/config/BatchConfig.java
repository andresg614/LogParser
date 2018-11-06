package com.ef.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

import com.ef.model.dto.LogRequesterDTO;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job logFileJob() {
		return jobBuilderFactory.get("logFileJob").incrementer(new RunIdIncrementer()).start(step1()).build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<LogRequesterDTO, LogRequesterDTO>chunk(10000)
				.reader(reader(null))
				.writer(writer())
				.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<LogRequesterDTO> reader(@Value("#{jobParameters}") Map<String,Object> jobParameters) {
		FlatFileItemReader<LogRequesterDTO> reader = new FlatFileItemReader<LogRequesterDTO>();
		reader.setResource(new FileSystemResource(jobParameters.get("filePath").toString()));

		reader.setLineMapper(new DefaultLineMapper<LogRequesterDTO>() {{			
			setLineTokenizer(new DelimitedLineTokenizer(){{
				setDelimiter("|");
				setNames(new String[] {"date", "ip", "request", "status", "userAgent"});
			}});
			
			setFieldSetMapper(new BeanWrapperFieldSetMapper<LogRequesterDTO>() {{
				setTargetType(LogRequesterDTO.class);
			}});
		}});

		return reader;
	}

	@Bean
	public DBItemWriter<LogRequesterDTO> writer() {
		return new DBItemWriter<LogRequesterDTO>();
	}
}