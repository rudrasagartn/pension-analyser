package com.pfm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity(name = "PFM_SCHEME")
public class PensionFundManagerSchemes {
	@Getter
	@Setter
	@Id
	@Column(name = "SCHEME_ID")
	private String id;
	@Getter
	@Setter
	@Column(name = "SCHEME_NAME")
	private String name;
	@Getter
	@Setter
	@Column(name = "PFM_ID")
	private String pfm_id;
	@Getter
	@Setter
	@Column(name = "PFM_NAME")
	private String pfm_name;

}
