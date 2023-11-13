package com.pfm.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;
@Repository
public interface INetAssetValueCustomDao extends CrudRepository<NetAssetValue, NAVCompositeKey> {

	@Query(nativeQuery = true,value="select * from nav a where a.nav_date>:navDate")
	public List<NetAssetValue> findByDate(Date navDate);

}
