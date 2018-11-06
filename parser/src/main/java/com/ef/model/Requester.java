package com.ef.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.ef.model.dto.ReportRequesterDTO;

@SqlResultSetMapping(name="OrderResults",
classes={
    @ConstructorResult(targetClass=ReportRequesterDTO.class,
        columns={
            @ColumnResult(name="id", type=Long.class),
            @ColumnResult(name="ip_address", type=String.class),
            @ColumnResult(name="total", type=Integer.class)
        })
}
)

@Entity
@Table(name = "requester")
public class Requester {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "status")
	private String status;

	@OneToMany(mappedBy = "requester", cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST})
    private List<Request> requests = new ArrayList<Request>();
	
	@OneToMany(mappedBy = "requester", cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST})
    private List<ReportDetail> reportDetails = new ArrayList<ReportDetail>();

	public Requester() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Requester(String ipAddress, String status, List<Request> requests, List<ReportDetail> reportDetails) {
		super();
		this.ipAddress = ipAddress;
		this.status = status;
		this.requests = requests;
		this.reportDetails = reportDetails;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ip_address
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ip_address the ip_address to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the requests
	 */
	public List<Request> getRequests() {
		return requests;
	}

	/**
	 * @param requests the requests to set
	 */
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	/**
	 * @return the reportDetails
	 */
	public List<ReportDetail> getReportDetails() {
		return reportDetails;
	}

	/**
	 * @param reportDetails the reportDetails to set
	 */
	public void setReportDetails(List<ReportDetail> reportDetails) {
		this.reportDetails = reportDetails;
	}
	
	/**
	 * Add a request to the list of requests
	 */
    public void addToRequests(Request request) {
    	request.setRequester(this);
        this.requests.add(request);
    }
    
	/**
	 * Add a reportDetail to the list of reportDetails
	 */
    public void addToReportDetails(ReportDetail reportDetail) {
    	reportDetail.setRequester(this);
        this.reportDetails.add(reportDetail);
    }
}