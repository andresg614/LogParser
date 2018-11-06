package com.ef.service;

import java.util.List;

import com.ef.model.ReportDetail;

public interface ReportDetailService {

	void add(ReportDetail reportDetail);

	List<ReportDetail> listReportDetails();
}