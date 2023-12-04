package springboot.jpaManager.service;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import springboot.jpaManager.TestUtils;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.AddressDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.repository.entityManager.EmCompanyRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyService통합테스트 {
    private EmCompanyRepository companyRepository;
    @Autowired
    TestEntityManager tem;
    EntityManager em;
    @PersistenceContext
    EntityManager entityManager;
    ModelMapper mapper= new ModelMapper();
    Utils utils= new Utils(mapper);

    @Test
    public void saveCompany_success() {
        //given

        em= tem.getEntityManager();
        Company company = utils.map(TestUtils.createCompanyDTO("회사2"), Company.class);
        Long id = companyRepository.save(company);
        //when
        Company foundCompany = em.find(Company.class, id);
        //then
        Assertions.assertThat(company.getName()).isEqualTo(foundCompany.getName());
    }

    @Test
    public void updateCompany() {
        em= tem.getEntityManager();

        //given
        Company company = companyRepository.findOne(0L);
        CompanyDTO.UpdateAll updateDTO = new CompanyDTO.UpdateAll();
        updateDTO.setName("회사2");
        AddressDTO addressDTO = new AddressDTO("city2", "street2", "zipcode2");
        updateDTO.setAddress(addressDTO);
        //when
        company.updateAll(updateDTO);
//        companyRepository.flush();
        //then
        Company found = em.find(Company.class, company.getId());
        Assertions.assertThat(found.getName()).isEqualTo("회사2");
    }
//
//    public void testDeleteCompany() {
//    }
//
//    public void testFindOne() {
//    }
//
//    public void testFindAll() {
//    }

    @AfterEach
    public void destroy(){
        em.close();
    }


    @PostConstruct
    public void init(){
        companyRepository= new EmCompanyRepository(entityManager);
        // private생성자 매핑 허용
        mapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        // Member entity to DTO 매핑설정
        mapper.typeMap(Member.class, MemberDTO.List.class).addMappings(mapper -> {
            mapper.map(Member -> Member.getTeam().getName(), MemberDTO.List::setTeamName);
            mapper.map(Member -> Member.getTeam().getCompany().getName(), MemberDTO.List::setCompanyName);
        });

        // Team entity to DTO 매핑설정
        mapper.typeMap(Team.class, TeamDTO.Update.class).addMappings(mapper -> {
            mapper.map(Team -> Team.getCompany().getId(), TeamDTO.Update::setCompanyId);
            mapper.map(Team -> Team.getCompany().getName(), TeamDTO.Update::setCompanyName);
        });
    }
}