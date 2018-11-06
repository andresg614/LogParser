package com.ef.dao;

import java.util.List;

import com.ef.model.Report;

public interface ReportDAO {

	void add(Report report);

	List<Report> listReports();
}