package com.pfm.service;

import java.util.List;

import com.pfm.converter.INAVConverter;
import com.pfm.dto.NetAssetValueDTO;

public interface INetAssetValueService extends INAVConverter{

	Boolean save(List<NetAssetValueDTO> navDTOs);

	NetAssetValueDTO getLatestNAV();

}
