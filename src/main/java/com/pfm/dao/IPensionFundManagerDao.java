package com.pfm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfm.model.NAVCompositeKey;
import com.pfm.model.PensionFundManager;

public interface IPensionFundManagerDao extends JpaRepository<PensionFundManager, NAVCompositeKey>{
	
	

}
