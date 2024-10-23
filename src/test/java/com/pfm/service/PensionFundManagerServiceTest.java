package com.pfm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.pfm.dao.IPensionFundManagerDao;
import com.pfm.dao.IPensionFundManagerDaoCustom;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.service.impl.PFMServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author rudrasagar.tn
 */
@ExtendWith(SpringExtension.class)
class PensionFundManagerServiceTest {

  @InjectMocks
  PFMServiceImpl pfmService;

  @Mock
  IPensionFundManagerDao iPensionFundManagerDao;

  @Mock
  IPensionFundManagerDaoCustom iPensionFundManagerDaoCustom;

  @Mock
  Environment environment;

  List<PensionFundManagerDTO> pfmList = null;

  PensionFundManagerDTO dto = null;

  String pensionFundManagerName = "HDFC Pension Management Company Limited";

  String pensionFundManagerId = "SM008";


  @BeforeEach
  void setUp() {
    pfmList = new ArrayList<>();

    dto = PensionFundManagerDTO.builder().id(pensionFundManagerId).name(pensionFundManagerName)
        .build();

    pfmList.add(dto);
  }


  @Test
  void givenPFMInstance_whenFindByNameInvoked_thenReturnPFMInstance() {
    //given ( preconditions / setup )
    PensionFundManagerDTO dto = new PensionFundManagerDTO("SM001",
        "SBI Pension Funds Private Limited");
    given(pfmService.findByName("SBI Pension Funds Private Limited")).willReturn(dto);

    //when ( action / behavior tested )
    PensionFundManagerDTO result = pfmService.findByName("SBI Pension Funds Private Limited");

    //then ( verify the output )
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(dto.getId());

  }

  @Test
  void given_whenGetAllInvoked_thenReturnListOfPensionFundMangerDTO() {
    //Given condition
    given(pfmService.getAll()).willReturn(pfmList);

    //when executed
    List<PensionFundManagerDTO> pfmList2 = pfmService.getAll();

    //then expected result
    assertThat(pfmList2).isNotNull();
    assertThat(dto.getId()).isEqualTo(pfmList2.get(0).getId());

  }

  @Test
  void givenPensionFundManagerName_whenFindByNameInvoked_thenReturnPensionFundManagerDTO() {
    //Given condition
    given(pfmService.findByName(pensionFundManagerName)).willReturn(dto);

    //when executed
    PensionFundManagerDTO hdfcPensionManagementCompanyLimited = pfmService.findByName(
        pensionFundManagerName);

    //then verify
    assertThat(hdfcPensionManagementCompanyLimited).isNotNull();
    assertThat(hdfcPensionManagementCompanyLimited.getName()).isEqualTo(pensionFundManagerName);
  }

  @Test
  void givenPensionFundMangerId_whenFindByIdInvoked_thenReturnPensionFundManagerDTO() {

    //given condition
    given(pfmService.findById(pensionFundManagerId)).willReturn(dto);

    //when executed
    PensionFundManagerDTO byId = pfmService.findById(pensionFundManagerId);

    //then verify
    assertThat(byId.getId()).isEqualTo(dto.getId());
  }

  @Test
  void givenPensionFundManagerName_whenFindByNameLikeInvoked_thenReturnPensionFundManagerDTO() {

    //given condition
    given(pfmService.findByNameLike("HDFC")).willReturn(pfmList);

    //when executed
    List<PensionFundManagerDTO> pfmDtoList = pfmService.findByNameLike("HDFC");

    //then verify
    assertThat(pfmDtoList.size()).isGreaterThan(0);
  }
}
