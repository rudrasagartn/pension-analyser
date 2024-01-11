package com.pfm.dao;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NetAssetValue;

public interface INetAssetValueDaoJdbcTemplate {

	public List<NetAssetValueDTO> queryForList(String sql, Date navDate);

	public List<NetAssetValueDTO> queryForList(String sql, String param);

	public NetAssetValueDTO queryForObject(String sql, String schemeId, String date);

	boolean insertBatch(List<NetAssetValue> navs);

	RowMapper<NetAssetValueDTO> rowMapper = (rs, rowNum) -> new NetAssetValueDTO(rs.getBigDecimal("nav"),
			rs.getDate("nav_date"), rs.getString("scheme_id"));
}
