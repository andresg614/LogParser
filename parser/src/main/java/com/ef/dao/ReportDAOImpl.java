package com.ef.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.ef.model.Report;

@Repository
public class ReportDAOImpl implements ReportDAO {

	@PersistenceContext
	private EntityManager entityMgr;

	@Override
	public void add(Report report) {
		entityMgr.persist(report);
	}

	@Override
	public List<Report> listReports() {
		CriteriaQuery<Report> criteriaQuery = entityMgr.getCriteriaBuilder().createQuery(Report.class);

		return entityMgr.createQuery(criteriaQuery).getResultList();
	}
}