/**
 * 
 */
package com.pfm.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pfm.dao.IPensionFundManagerSchemesDAOJdbcTemplate;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.exception.ResourceNotFoundException;

/**
 * @author rudrasagar.tn
 *
 */
@Repository
public class PensionFundManagerSchemesDAOJdbcTemplateImpl implements IPensionFundManagerSchemesDAOJdbcTemplate {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	private List<PensionFundManagerSchemesDTO> results;

	@Override
	public PensionFundManagerSchemesDTO findByName(String pfmsName) {

		return jdbcTemplate.query(environment.getProperty("pfms.findByName"), rowMapper, pfmsName).stream().findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(pfmsName + " : " + HttpStatus.NOT_FOUND.toString()));
	}

	public List<PensionFundManagerSchemesDTO> executeQuery(String sql, String queryParam) {
		results = jdbcTemplate.query(sql, rowMapper, queryParam);
		if (CollectionUtils.isEmpty(results)) {
			throw new ResourceNotFoundException(queryParam + " : " + HttpStatus.NOT_FOUND.toString());
		}
		return results;
	}

	/*
	 * @Override public List<PensionFundManagerSchemesDTO> findByNameLike(String
	 * pfmsName) { String sql = environment.getProperty("pfms.findByNameLike");
	 * return executeQuery(sql, pfmsName); }
	 * 
	 * 
	 * @Override public List<PensionFundManagerSchemesDTO>
	 * findSchemesByFundManager(String pfmName) { String sql =
	 * environment.getProperty("pfms.findSchemesByFundManager"); return
	 * executeQuery(sql, pfmName); }
	 */
}
