package com.pfm.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.model.PensionFundManager;

@DataJpaTest
@PropertySources({ @PropertySource("config.properties"), @PropertySource("queries.properties") })
class PensionFundManagerDaoTest {

	@Autowired
	IPensionFundManagerDao dao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	PensionFundManager fundManager = null;

	@BeforeEach
	public void init() {
		fundManager = new PensionFundManager("SM01", "Aditya Birla Sunlife Pension Management Limited");

	}

	@Test
	void givenPFMangerInstance_whenFindAllInvocked_thenReturnListOfPFManagers() {
		// given ( preconditions / setup )
		dao.save(fundManager);

		// when ( action / behavior tested )
		List<PensionFundManager> result = dao.findAll();
		// then ( verify the output )
		assertThat(result).isNotEmpty();
		for (PensionFundManager pensionFundManager : result) {
			assertThat(pensionFundManager.getId()).isEqualTo(fundManager.getId());
			assertThat(pensionFundManager.getName()).isEqualTo(fundManager.getName());
		}
	}

	@Test
	void givenPFMangerInstance_whenFindByNameInvocked_thenReturnPFManagerDto() {
		// given ( preconditions / setup )
		jdbcTemplate.update("insert into pfm (pfm_id,name) values(?,?)", fundManager.getId(), fundManager.getName());

		// when ( action / behavior tested )
		Optional<PensionFundManagerDTO> dto = jdbcTemplate
				.query("select * from pfm where name=?", pfmDtoRowMapper, fundManager.getName()).stream().findFirst();

		// then ( verify the output )
		assertThat(dto.get()).isNotNull();
		assertThat(dto.get().getName()).isEqualTo(fundManager.getName());
	}

	RowMapper<PensionFundManagerDTO> pfmDtoRowMapper = (rs, rowNum) -> new PensionFundManagerDTO(rs.getString("pfm_id"),
			rs.getString("name"));

}
