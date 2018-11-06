package com.ef.service;

import java.util.List;

import com.ef.model.Report;

public interface ReportService {
	void add(Report report);

	List<Report> listReports();
}