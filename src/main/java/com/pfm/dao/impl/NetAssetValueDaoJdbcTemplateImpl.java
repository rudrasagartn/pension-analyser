package com.pfm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.pfm.dao.INetAssetValueDaoJdbcTemplate;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.exception.ResourceNotFoundException;
import com.pfm.model.NetAssetValue;

@Repository
public class NetAssetValueDaoJdbcTemplateImpl implements INetAssetValueDaoJdbcTemplate {

	private static Logger log = LoggerFactory.getLogger(NetAssetValueDaoJdbcTemplateImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<NetAssetValueDTO> findByDate(String sql, Date navDate) {
		logInfo(sql, navDate+"");
		return jdbcTemplate.query(sql, rowMapper, navDate);
	}

	@Override
	public NetAssetValueDTO findNAVforScheme(String sql, String schemeId, String date) {
		logInfo(sql, schemeId, date);
		return jdbcTemplate.query(sql, rowMapper, date, schemeId).stream().findFirst().orElseThrow(
				() -> new ResourceNotFoundException(schemeId + ":" + date + ":" + HttpStatus.NOT_FOUND.toString()));
	}

	@Override
	public List<NetAssetValueDTO> queryForList(String sql, Date navDate) {
		logInfo(sql, navDate+"");
		return jdbcTemplate.query(sql, rowMapper, navDate);
	}
	@Override
	public List<NetAssetValueDTO> queryForList(String sql, String param) {
		logInfo(sql, param);
		return jdbcTemplate.query(sql, rowMapper, param);
	}

	@Override
	public NetAssetValueDTO queryForObject(String sql, String schemeId, String date) {
		logInfo(sql, schemeId, date);
		return jdbcTemplate.query(sql, rowMapper, date, schemeId).stream().findFirst().orElseThrow(
				() -> new ResourceNotFoundException(schemeId + ":" + date + ":" + HttpStatus.NOT_FOUND.toString()));
	}

	
	
	@Override
	public boolean insertBatch(final List<NetAssetValue> navs) {

		String sql = "insert ignore into nav (nav,nav_date,scheme_id) values(?,?,?)";

		jdbcTemplate.batchUpdate(sql, navs, 1000, new ParameterizedPreparedStatementSetter<NetAssetValue>() {
			@Override
			public void setValues(PreparedStatement ps, NetAssetValue navModel) throws SQLException {
				ps.setBigDecimal(1, navModel.getNav());
				ps.setDate(2, new java.sql.Date(navModel.getNavCompositeKey().getNavDate().getTime()));
				ps.setString(3, navModel.getNavCompositeKey().getScheme_id());
			}

		});
		return true;
	}
	
	private void logInfo(String sql, String navDate) {
		log.info("executing query {} with param {}", sql, navDate);
	}
	
	private void logInfo(String sql, String schemeId, String date) {
		log.info("executing query {} with param {}, {}", sql, date, schemeId);
	}
}
