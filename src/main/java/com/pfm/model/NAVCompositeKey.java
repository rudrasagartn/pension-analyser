package com.pfm.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@RequiredArgsConstructor
@Access(AccessType.FIELD)
public class NAVCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	@Column(name = "nav_date")
	private Date navDate;
	@Getter
	@Setter
	@Column(name = "scheme_id")
	private String scheme_id;

}
