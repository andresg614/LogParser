package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.dao.ReportDetailDAO;
import com.ef.model.ReportDetail;

@Service
public class ReportDetailServiceImpl implements ReportDetailService {

	@Autowired
	private ReportDetailDAO reportDetailDao;

	@Transactional
	@Override
	public void add(ReportDetail reportDetail) {
		reportDetailDao.add(reportDetail);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReportDetail> listReportDetails() {
		return reportDetailDao.listReportDetails();
	}

}