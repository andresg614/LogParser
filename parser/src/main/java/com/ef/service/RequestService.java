package com.ef.service;

import java.util.List;

import com.ef.model.Request;

public interface RequestService {
	
	void add(Request request);

	List<Request> listRequests();
}