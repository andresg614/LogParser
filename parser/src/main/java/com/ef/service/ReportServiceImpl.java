package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.dao.ReportDAO;
import com.ef.model.Report;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDAO reportDao;

	@Transactional
	@Override
	public void add(Report report) {
		reportDao.add(report);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Report> listReports() {
		return reportDao.listReports();
	}

}