package com.ef.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "request")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "request_date")
	private Timestamp requestDate;

	@Column(name = "request_description")
	private String requestDescription;

	@Column(name = "request_status")
	private String requestStatus;

	@Column(name = "request_user_agent")
	private String requestUserAgent;

    @ManyToOne
    @JoinColumn(name = "id_requester", referencedColumnName = "id")
    private Requester requester = new Requester();

	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Request(Timestamp requestDate, String requestDescription, String requestStatus, String requestUserAgent,
			Requester requester) {
		super();
		this.requestDate = requestDate;
		this.requestDescription = requestDescription;
		this.requestStatus = requestStatus;
		this.requestUserAgent = requestUserAgent;
		this.requester = requester;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the requestDate
	 */
	public Timestamp getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate
	 *            the requestDate to set
	 */
	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @return the requestDescription
	 */
	public String getRequestDescription() {
		return requestDescription;
	}

	/**
	 * @param requestDescription
	 *            the requestDescription to set
	 */
	public void setRequestDescription(String requestDescription) {
		this.requestDescription = requestDescription;
	}

	/**
	 * @return the requestStatus
	 */
	public String getRequestStatus() {
		return requestStatus;
	}

	/**
	 * @param requestStatus
	 *            the requestStatus to set
	 */
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	/**
	 * @return the requestUserAgent
	 */
	public String getRequestUserAgent() {
		return requestUserAgent;
	}

	/**
	 * @param requestUserAgent
	 *            the requestUserAgent to set
	 */
	public void setRequestUserAgent(String requestUserAgent) {
		this.requestUserAgent = requestUserAgent;
	}

	/**
	 * @return the requester
	 */
	public Requester getRequester() {
		return requester;
	}

	/**
	 * @param requester
	 *            the requester to set
	 */
	public void setRequester(Requester requester) {
		this.requester = requester;
	}
}