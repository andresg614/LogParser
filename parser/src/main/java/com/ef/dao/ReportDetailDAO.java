package com.ef.dao;

import java.util.List;

import com.ef.model.ReportDetail;

public interface ReportDetailDAO {

	void add(ReportDetail reportDetail);

	List<ReportDetail> listReportDetails();
}