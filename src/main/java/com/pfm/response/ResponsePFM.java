package com.pfm.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "data")
public class ResponsePFM {
	/*
	 * private PFMResponse data = new PFMResponse();
	 * 
	 * public PFMResponse getData() { return data; }
	 * 
	 * public void setData(PFMResponse data) { this.data = data; }
	 */

	List<PFMResponse> data = new ArrayList<>();

}
