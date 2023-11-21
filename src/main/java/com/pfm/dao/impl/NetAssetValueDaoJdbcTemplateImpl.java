package com.pfm.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pfm.dao.INetAssetValueDaoJdbcTemplate;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.exception.ResourceNotFoundException;

@Repository
public class NetAssetValueDaoJdbcTemplateImpl implements INetAssetValueDaoJdbcTemplate {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<NetAssetValueDTO> findByDate(String sql, Date navDate) {
		return jdbcTemplate.query(sql, rowMapper, navDate);
	}

	@Override
	public NetAssetValueDTO findNAVforScheme(String sql, String schemeId, String date) {
		return jdbcTemplate.query(sql, rowMapper, date, schemeId).stream().findFirst().get();
	}

	@Override
	public List<NetAssetValueDTO> queryForList(String sql, Date navDate) {
		return jdbcTemplate.query(sql, rowMapper, navDate);
	}
	@Override
	public List<NetAssetValueDTO> queryForList(String sql, String param) {
		return jdbcTemplate.query(sql, rowMapper, param);
	}

	@Override
	public NetAssetValueDTO queryForObject(String sql, String schemeId, String date) {
		return jdbcTemplate.query(sql, rowMapper, date, schemeId).stream().findFirst().orElseThrow(
				() -> new ResourceNotFoundException(schemeId + ":" + date + ":" + HttpStatus.NOT_FOUND.toString()));
	}

}
