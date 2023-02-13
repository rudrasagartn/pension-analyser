package com.pfm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class PensionFundManagerSchemesDTO {

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
