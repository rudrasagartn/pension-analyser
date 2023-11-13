package com.pfm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;

public interface INetAssetValueDao extends JpaRepository<NetAssetValue, NAVCompositeKey> {

	/*
	 * @Query("from NetAssetValue nav where nav.navCompositeKey.navDate= :date")
	 * List<NetAssetValue> findByNavCompositeKey_NavDate(@Param("date") Date date);
	 */

}
