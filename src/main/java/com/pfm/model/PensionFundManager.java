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
@Entity(name = "pfm")
public class PensionFundManager {

	@Getter
	@Setter
	@Id
	@Column(name = "pfm_id")
	private String id;
	@Getter
	@Setter
	@Column(name="name")
	private String name;

}
