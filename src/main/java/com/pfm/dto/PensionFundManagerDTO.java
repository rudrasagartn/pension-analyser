package com.pfm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class PensionFundManagerDTO implements Serializable{

	private static final long serialVersionUID = -7059944496964365593L;
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String name;

	/*
	 * public String getId() { return id; }
	 * 
	 * public void setId(String id) { this.id = id; }
	 * 
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */
}
