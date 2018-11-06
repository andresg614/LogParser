package com.ef.dao;

import java.util.List;

import com.ef.model.Request;

public interface RequestDAO {

	void add(Request request);

	List<Request> listRequests();
}