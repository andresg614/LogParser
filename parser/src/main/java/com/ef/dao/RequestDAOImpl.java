package com.ef.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.ef.model.Request;

@Repository
public class RequestDAOImpl implements RequestDAO {

   @PersistenceContext
   private EntityManager entityMgr;

   @Override
   public void add(Request request) {
      entityMgr.persist(request);
   }

   @Override
   public List<Request> listRequests() {
      CriteriaQuery<Request> criteriaQuery = entityMgr.getCriteriaBuilder().createQuery(Request.class);

      return entityMgr.createQuery(criteriaQuery).getResultList();
   }
}