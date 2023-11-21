/**
 * 
 */
package com.pfm.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<PensionFundManagerSchemesDTO> fetchRecords(String sql, String queryParam) {
		List<PensionFundManagerSchemesDTO> results = jdbcTemplate.query(sql, rowMapper, queryParam);
		if (CollectionUtils.isEmpty(results)) {
			throw new ResourceNotFoundException(queryParam + " : " + HttpStatus.NOT_FOUND.toString());
		}
		return results;
	}

	public PensionFundManagerSchemesDTO fetch(String sql, String queryParam) {
		return jdbcTemplate.query(sql, rowMapper, queryParam).stream().findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(queryParam + " : " + HttpStatus.NOT_FOUND.toString()));
	}
}
