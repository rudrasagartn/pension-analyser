package com.pfm.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;

public interface INetAssetValueDao extends JpaRepository<NetAssetValue, NAVCompositeKey> {
	/*
	 * @Query("from NetAssetValue.NAVCompositeKey where navDate= :date")
	 * List<NetAssetValue> findByNavCompositeKey_NavDate(@Param("date") Date date);
	 */
}
