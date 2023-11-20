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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pfm.dao.IPensionFundManagerDaoCustom;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.exception.ResourceNotFoundException;

/**
 * @author rudrasagar.tn
 */
@Repository
public class PensionFundManagerDaoCustomImpl implements IPensionFundManagerDaoCustom {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public PensionFundManagerDTO findByName(String name) {
		return executeQuery(name, environment.getProperty("pfm.findByName"));
	}

	@Override
	public PensionFundManagerDTO findById(String id) {
		return executeQuery(id, environment.getProperty("pfm.findById"));
	}

	private PensionFundManagerDTO executeQuery(String arg, String sql) {
		return jdbcTemplate.query(sql, pfmDtoRowMapper, arg).stream().findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(arg + " : " + HttpStatus.NOT_FOUND.toString()));
	}

	@Override
	public List<PensionFundManagerDTO> findByNameLike(String name) {
		List<PensionFundManagerDTO> result = jdbcTemplate.query(environment.getProperty("pfm.findByNameLike"),
				pfmDtoRowMapper, "%" + name + "%");
		if (CollectionUtils.isEmpty(result)) {
			throw new ResourceNotFoundException(name + " : " + HttpStatus.NOT_FOUND.toString());
		}
		return result;
	}
	RowMapper<PensionFundManagerDTO> pfmDtoRowMapper = (rs, rowNum) -> new PensionFundManagerDTO(rs.getString("pfm_id"),
			rs.getString("name"));
}
