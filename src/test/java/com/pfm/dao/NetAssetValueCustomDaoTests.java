package com.pfm.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;
import com.pfm.util.Utils;

@DataJpaTest
public class NetAssetValueCustomDaoTests {
	
	@Autowired
	INetAssetValueCustomDao dao;
	
	@Test
	public void givenNAVInstance_whenFindByDateInvoked_thenReturnListOfNAVInstance() {
		//given ( preconditions / setup )
		NetAssetValue nav = new NetAssetValue();
		nav.setNav(BigDecimal.TEN);
		nav.setNavCompositeKey(new NAVCompositeKey(new Date(),"SM001"));
		dao.save(nav);
		//when ( action / behavior tested )
		List<NetAssetValue> list= dao.findByDate(Utils.getPreviousDays(5));
		
		//then ( verify the output )
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isOne();
	}

}
