package com.pfm.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.pfm.dto.PensionFundManagerSchemesDTO;

/**
 * @author rudrasagar.tn
 */
public interface IPensionFundManagerSchemesDAOJdbcTemplate {

	List<PensionFundManagerSchemesDTO> fetchRecords(String sql, String queryParam);

	RowMapper<PensionFundManagerSchemesDTO> rowMapper = (resultSet, rowNum) -> new PensionFundManagerSchemesDTO(
			resultSet.getString("scheme_id"), resultSet.getString("scheme_name"), resultSet.getString("pfm_id"),
			resultSet.getString("pfm_name"));

	PensionFundManagerSchemesDTO fetch(String sql, String queryParam);

}
