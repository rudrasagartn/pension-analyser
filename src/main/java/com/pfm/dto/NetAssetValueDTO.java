package com.pfm.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.ToString;

@ToString
public class NetAssetValueDTO {

	
	private BigDecimal nav;
	
	private Date date;
	
	private String scheme_id;
	
	

	public NetAssetValueDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NetAssetValueDTO(BigDecimal nav, Date date, String scheme_id) {
		super();
		this.nav = nav;
		this.date = date;
		this.scheme_id = scheme_id;
	}

	public BigDecimal getNav() {
		return nav;
	}

	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	
	

}
