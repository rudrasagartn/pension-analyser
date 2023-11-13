package com.pfm.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pfm.model.PensionFundManager;

@DataJpaTest
public class PensionFundManagerDaoTest {

	@Autowired
	IPensionFundManagerDao dao;

	@Test
	public void givenPFMangerInstance_whenFindAllInvocked_thenReturnListOfPFManagers() {
		// given ( preconditions / setup )
		PensionFundManager fundManager = new PensionFundManager("SM01",
				"Aditya Birla Sunlife Pension Management Limited");
		List<PensionFundManager> pfmList = new ArrayList<>();
		pfmList.add(fundManager);
		dao.saveAll(pfmList);
		// when ( action / behavior tested )
		List<PensionFundManager> result = dao.findAll();
		// then ( verify the output )
		assertThat(result).isNotEmpty();
		for (PensionFundManager pensionFundManager : result) {
			assertThat(pensionFundManager.getId()).isEqualTo(fundManager.getId());
			assertThat(pensionFundManager.getName()).isEqualTo(fundManager.getName());
		}
	}

}
