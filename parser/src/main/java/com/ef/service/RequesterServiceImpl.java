package com.ef.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.dao.RequesterDAO;
import com.ef.model.Requester;

@Service
public class RequesterServiceImpl implements RequesterService {

	@Autowired
	private RequesterDAO requesterDao;

	@Transactional
	@Override
	public void add(Requester requester) {
		requesterDao.add(requester);
	}
	
	@Transactional
	@Override
	public void merge(Requester requester) {
		requesterDao.merge(requester);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Requester> listRequesters() {
		return requesterDao.listRequesters();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Requester> findByIpAddress(String ipAddress) {
		return requesterDao.findByIpAddress(ipAddress);
	}	
	
	@Transactional(readOnly = true)
	@Override
	public List<Requester> findByOverThreshold(String threshold, Timestamp startDate, Timestamp endDate) {
		return requesterDao.findByOverThreshold(threshold, startDate, endDate);
	}
}