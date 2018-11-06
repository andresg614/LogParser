package com.ef.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.ef.model.ReportDetail;

@Repository
public class ReportDetailDAOImpl implements ReportDetailDAO {

	@PersistenceContext
	private EntityManager entityMgr;

	@Override
	public void add(ReportDetail reportDetail) {
		entityMgr.persist(reportDetail);
	}

	@Override
	public List<ReportDetail> listReportDetails() {
		CriteriaQuery<ReportDetail> criteriaQuery = entityMgr.getCriteriaBuilder().createQuery(ReportDetail.class);

		return entityMgr.createQuery(criteriaQuery).getResultList();
	}
}