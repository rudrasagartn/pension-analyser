package com.pfm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;

public interface INetAssetValueDao extends JpaRepository<NetAssetValue, NAVCompositeKey> {

}
