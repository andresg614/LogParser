package com.ef.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.ef.model.ReportDetail;
import com.ef.model.Requester;
import com.ef.model.dto.ReportRequesterDTO;


@Repository
public class RequesterDAOImpl implements RequesterDAO {

	@PersistenceContext
	private EntityManager entityMgr;

	@Override
	public void add(Requester requester) {
		entityMgr.persist(requester);
	}
	
	@Override
	public void merge(Requester requester) {
		entityMgr.merge(requester);
	}

	@Override
	public List<Requester> listRequesters() {
		CriteriaQuery<Requester> criteriaQuery = entityMgr.getCriteriaBuilder().createQuery(Requester.class);

		return entityMgr.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<Requester> findByIpAddress(String ipAddress) {
        CriteriaQuery<Requester> criteriaQuery = entityMgr.getCriteriaBuilder().createQuery(Requester.class);
        
        Root<Requester> requester = criteriaQuery.from(Requester.class);
        
        criteriaQuery.select(requester)
        	.where(entityMgr.getCriteriaBuilder().equal(requester.get("ipAddress"), ipAddress));
        
        return entityMgr.createQuery(criteriaQuery).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Requester> findByOverThreshold(String threshold, Timestamp startDate, Timestamp endDate) {
		List<Requester> listRequesters = new ArrayList<Requester>();
		
		Query query = entityMgr.createNativeQuery(
			"SELECT rr.id as id, rr.ip_address as ip_address, countview.total as total " +  
			"FROM requester AS rr, (SELECT id_requester as idrt, count(*) as total " +
									"FROM request AS rt " +
									"WHERE rt.request_date >= ?1 " +
									"AND rt.request_date <= ?2 " +
									"GROUP BY id_requester) AS countview " +
			"WHERE rr.id = countview.idrt " +
			"AND countview.total > ?3 " +
			"ORDER BY rr.ip_address", "OrderResults")
				.setParameter(1, startDate.toString())
				.setParameter(2, endDate.toString())
				.setParameter(3, threshold);
		
		List<ReportRequesterDTO> listReportRequesterDTO = query.getResultList();

		for (int i=0;i<listReportRequesterDTO.size();i++) {
			Requester requesterTemp = new Requester();
			requesterTemp.setId(listReportRequesterDTO.get(i).getId());
			requesterTemp.setIpAddress(listReportRequesterDTO.get(i).getIp_address());
			requesterTemp.setStatus("BLOCKED");

			ReportDetail reportDetailTemp = new ReportDetail();
			reportDetailTemp.setRequestCount(listReportRequesterDTO.get(i).getTotal());
			reportDetailTemp.setStatus("OVER THRESHOLD");
			
			requesterTemp.addToReportDetails(reportDetailTemp);	
			
			listRequesters.add(requesterTemp);
		}
		
		return listRequesters;
	}
}