package com.ef.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "report_detail")
public class ReportDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "status")
	private String status;

	@Column(name = "request_count")
	private Integer requestCount;

	@OneToOne
    @JoinColumn(name = "id_report", referencedColumnName = "id")
	private Report report;

    @OneToOne
    @JoinColumn(name = "id_requester", referencedColumnName = "id")
	private Requester requester;

	public ReportDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportDetail(String status, Integer requestCount, Report report, Requester requester) {
		super();
		this.status = status;
		this.requestCount = requestCount;
		this.report = report;
		this.requester = requester;
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
	 * @return the requestCount
	 */
	public Integer getRequestCount() {
		return requestCount;
	}

	/**
	 * @param requestCount the requestCount to set
	 */
	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}

	/**
	 * @return the report
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	/**
	 * @return the requester
	 */
	public Requester getRequester() {
		return requester;
	}

	/**
	 * @param requester the requester to set
	 */
	public void setRequester(Requester requester) {
		this.requester = requester;
	}
}