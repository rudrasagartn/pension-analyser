package com.pfm.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pfm.dao.IPensionFundManagerDao;
import com.pfm.dao.IPensionFundManagerDaoCustom;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.service.impl.PFMServiceImpl;

/**
 * @author rudrasagar.tn
 *
 */
@ExtendWith(SpringExtension.class)
public class PensionFundManagerServiceTest {
	
	@InjectMocks
	PFMServiceImpl pfmServiceImpl;
	
	@Mock
	IPensionFundManagerDao iPensionFundManagerDao;
	
	@Mock
	IPensionFundManagerDaoCustom iPensionFundManagerDaoCustom;
	
	@Mock
	Environment environment;
	
	
	@Test
	public void givenPFMInstance_whenFindByNameInvoked_thenReturnPFMInstance() {
		//given ( preconditions / setup )

		PensionFundManagerDTO dto = new PensionFundManagerDTO("SM001","SBI Pension Funds Private Limited");
		BDDMockito.given(pfmServiceImpl.findByName("SBI Pension Funds Private Limited")).willReturn(dto);
		//when ( action / behavior tested )
		PensionFundManagerDTO result = pfmServiceImpl.findByName("SBI Pension Funds Private Limited");
		//then ( verify the output )
		assertThat(result).isNotNull();
		
		assertThat(result.getId()).isEqualTo(dto.getId());
	}
	
	

}
