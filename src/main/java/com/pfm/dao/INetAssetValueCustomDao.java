package com.pfm.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;

public interface INetAssetValueCustomDao extends CrudRepository<NetAssetValue, NAVCompositeKey> {
	/*
	 * @Query("select a from NetAssetValue a where a.navCompositeKey.navDate=:navDate"
	 * ) public List<NetAssetValue> findByDate(Date navDate);
	 */

}
