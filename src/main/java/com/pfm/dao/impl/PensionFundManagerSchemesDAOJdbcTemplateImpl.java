/**
 * 
 */
package com.pfm.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pfm.dao.IPensionFundManagerSchemesDAOJdbcTemplate;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.exception.ResourceNotFoundException;

/**
 * @author rudrasagar.tn
 */
@Repository
public class PensionFundManagerSchemesDAOJdbcTemplateImpl implements IPensionFundManagerSchemesDAOJdbcTemplate {

	private static final Logger log = LoggerFactory.getLogger(PensionFundManagerSchemesDAOJdbcTemplateImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<PensionFundManagerSchemesDTO> fetchRecords(String sql, String queryParam) {
		logInfo(sql, queryParam);
		List<PensionFundManagerSchemesDTO> results = jdbcTemplate.query(sql, rowMapper, queryParam);
		if (CollectionUtils.isEmpty(results)) {
			throw new ResourceNotFoundException(queryParam + " : " + HttpStatus.NOT_FOUND.toString());
		}
		return results;
	}

	@Override
	public PensionFundManagerSchemesDTO fetch(String sql, String queryParam) {
		logInfo(sql, queryParam);
		return jdbcTemplate.query(sql, rowMapper, queryParam).stream().findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(queryParam + " : " + HttpStatus.NOT_FOUND.toString()));
	}

	private void logInfo(String sql, String queryParam) {
		log.info("executing fetchRecords with queryParam {} for sql {} ", queryParam, sql);
	}
}
