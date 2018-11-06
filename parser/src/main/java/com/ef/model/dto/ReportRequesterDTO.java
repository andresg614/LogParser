package com.ef.model.dto;

public class ReportRequesterDTO {		 
	private Long id;
	private String ip_address;
	private Integer total;
	
	public ReportRequesterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportRequesterDTO(Long id, String ip_address, Integer total) {
		super();
		this.id = id;
		this.ip_address = ip_address;
		this.total = total;
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
	public String getIp_address() {
		return ip_address;
	}

	/**
	 * @param ip_address the ip_address to set
	 */
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}