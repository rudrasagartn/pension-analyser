package com.pfm.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;

@DataJpaTest
public class NetAssetValueCustomDaoTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void givenNAVInstance_whenFindByDateInvoked_thenReturnListOfNAVInstance() {
		// given ( preconditions / setup )

		NetAssetValue nav = new NetAssetValue();
		nav.setNav(BigDecimal.TEN);
		nav.setNavCompositeKey(new NAVCompositeKey(new Date(), "SM001"));
		Calendar cal = Calendar.getInstance();
		cal.set(2023, 10, 10);
		jdbcTemplate.update("insert into nav (scheme_id,nav_date,nav) values(?,?,?)", "SM001", new Date(),
				BigDecimal.ONE);

		// when ( action / behavior tested )
		List<NetAssetValueDTO> list = jdbcTemplate.query("select * from nav a where a.nav_date>?", rowMapper,
				cal.getTime());

		// then ( verify the output ) assertThat(list).isNotEmpty();
		assertThat(list.size()).isOne();

	}

	@Test
	void givenNAVInstance_whenFindNavForSchemeInvoked_thenReturnNAVInstance() throws ParseException {
		// given ( preconditions / setup )
		NetAssetValue nav = new NetAssetValue();
		nav.setNav(BigDecimal.TEN);
		nav.setNavCompositeKey(new NAVCompositeKey(new Date(), "SM001"));

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse("2023-11-21");

		jdbcTemplate.update("insert into nav (scheme_id,nav_date,nav) values(?,?,?)", "SM001", date, BigDecimal.ONE);
		// when ( action / behavior tested )
		NetAssetValueDTO dto = jdbcTemplate
				.query("select * from nav a where nav_date=? and scheme_id=?", rowMapper, date, "SM001").stream()
				.findFirst().get();
		// then ( verify the output )
		assertThat(dto).isNotNull();
		assertThat(dto.getScheme_id()).isEqualTo(nav.getNavCompositeKey().getScheme_id());
	}

	RowMapper<NetAssetValueDTO> rowMapper = (rs, rowNum) -> new NetAssetValueDTO(rs.getBigDecimal("nav"),
			rs.getDate("nav_date"), rs.getString("scheme_id"));
}
