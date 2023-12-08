package springboot.jpaManager.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.AddressDTO;
import springboot.jpaManager.dto.CompanyDTO;

import java.util.List;

@SpringBootTest
public class CompanyService통합테스트 {
    @Autowired
    private CompanyService companyService;

    @Test
    void updateCompany() {

        //given
        Company company = companyService.findOne(1L);
        CompanyDTO.UpdateAll updateDTO = new CompanyDTO.UpdateAll();
        updateDTO.setName("회사2");
        AddressDTO addressDTO = new AddressDTO("city2", "street2", "zipcode2");
        updateDTO.setAddress(addressDTO);
        //when
        companyService.updateCompany(updateDTO);

        //then
//        Company found = em.find(Company.class, company.getId());
        Company update = companyService.findOne(1L);
        Assertions.assertThat(update.getName()).isEqualTo("회사2");
    }

    @Test
    void deleteCompany(){

        //given
        Company company = companyService.findByIdWithTeam(1L);
//        List<Team> teamList = company.getTeamList();

        //when
        companyService.deleteCompany(company.getId());


    }
0


}