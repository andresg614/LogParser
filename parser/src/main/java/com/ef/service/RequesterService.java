package com.ef.service;

import java.sql.Timestamp;
import java.util.List;

import com.ef.model.Requester;

public interface RequesterService {
	
	void add(Requester requester);
	
	void merge(Requester requester);

	List<Requester> listRequesters();
	
	List<Requester> findByIpAddress(String ipAddress);
	
	List<Requester> findByOverThreshold(String threshold, Timestamp startDate,  Timestamp endDate);
}