package com.pfm.service;

import java.util.List;

import com.pfm.converter.INAVConverter;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NetAssetValue;

public interface INetAssetValueService extends INAVConverter{

	Boolean save(List<NetAssetValueDTO> navDTOs);

	List<NetAssetValue> getLatestNAV();

}
