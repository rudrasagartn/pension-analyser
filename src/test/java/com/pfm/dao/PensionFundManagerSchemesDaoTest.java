package com.pfm.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pfm.model.PensionFundManager;
import com.pfm.model.PensionFundManagerSchemes;

@DataJpaTest
public class PensionFundManagerSchemesDaoTest {
	
	@Autowired
	IPensionFundManagerSchemesDAO dao;
	
	@Autowired
	IPensionFundManagerDao pfmDao;
	
	@Test
	public void givenListOfPFMSchemes_whenGetAllInvoked_thenReturnListOfPFMSchemes() {
		
		//given ( preconditions / setup )
		PensionFundManager pfm = new PensionFundManager("SM01", "Aditya Birla Sunlife Pension Management Limited");
		pfmDao.save(pfm);
		
		PensionFundManagerSchemes pfms = new PensionFundManagerSchemes();
		pfms.setId("SM010001");
		pfms.setName("ADITYA BIRLA SUNLIFE PENSION FUND SCHEME E - TIER I");
		pfms.setPfm_id("SM01");
		pfms.setPfm_name("Aditya Birla Sunlife Pension Management Limited");
		
		List<PensionFundManagerSchemes> pfmsList = new ArrayList<>();
		pfmsList.add(pfms);
		dao.saveAll(pfmsList);
		
		//when ( action / behavior tested )
		List<PensionFundManagerSchemes> result=dao.findAll();
		
		//then ( verify the output )
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isOne();
		for (PensionFundManagerSchemes object : result) {
			assertThat(object.getId()).isEqualTo(pfms.getId());
			assertThat(object.getName()).isEqualTo(pfms.getName());
			assertThat(object.getPfm_id()).isEqualTo(pfms.getPfm_id());
			assertThat(object.getPfm_name()).isEqualTo(pfms.getPfm_name());
		}
	}

}
