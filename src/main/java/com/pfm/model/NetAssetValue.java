package com.pfm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "nav")
public class NetAssetValue implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Column(name = "nav")
	private BigDecimal nav;

	
	@EmbeddedId
	NAVCompositeKey navCompositeKey;

}
