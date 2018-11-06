package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.dao.RequestDAO;
import com.ef.model.Request;

@Service
public class RequestServiceImpl implements RequestService {

   @Autowired
   private RequestDAO requestDao;

   @Transactional
   @Override
   public void add(Request Request) {
	   requestDao.add(Request);
   }

   @Transactional(readOnly = true)
   @Override
   public List<Request> listRequests() {
      return requestDao.listRequests();
   }

}