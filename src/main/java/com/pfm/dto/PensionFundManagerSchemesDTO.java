package com.pfm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class PensionFundManagerSchemesDTO implements Serializable {

	private static final long serialVersionUID = -5805951694854123583L;
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String pfm_id;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String pfm_name;

}
