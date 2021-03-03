package springboot.jpaManager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.*;
import springboot.jpaManager.dto.CompanyDTO;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class CompanyServiceTest {

    @Autowired
    CompanyService companyService;
    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;

    @Test
    public void saveCompany() throws Exception {

        //given
        Company company = this.createCompany();
        CompanyDTO companyDTO = companyService.createCompanyDTO(company);
        Long companyId = companyService.saveCompany(companyDTO);

        //when
        Company found = companyService.findOne(companyId);

        //then
        assertEquals(company, found);

    }

    @Test
    public void updateCompany() throws Exception {

        //given
        Company origin = this.createCompany();
        CompanyDTO originDTO = companyService.createCompanyDTO(origin);
        Long companyId = companyService.saveCompany(originDTO);
        companyService.flush();

        //when
        CompanyDTO companyDTO = this.createCompanyDTO();
        companyService.updateCompany(companyDTO);

        //then
        Company fresh = companyService.findOne(companyId);
        assertEquals(fresh.getName(), companyDTO.getName());

    }

    @Test
    public void deleteCompany() throws Exception {

        //given
        Member member = createMember();
        Long memberId = memberService.saveMember(member);

        Long teamId = member.getTeam().getId();
        Long companyId = member.getTeam().getCompany().getId();
        companyService.flush();

        //when
        companyService.deleteCompany(companyId);

        //then
        Company company = companyService.findOne(companyId);
        Team team = teamService.findOne(teamId);
        Member found = memberService.findOne(memberId);

        assertNull("컴퍼니를 지웠기때문에 null이떠야함", company);
        assertNull("캐스캐이딩으로 team도 널이 떠야함", team);
        assertNull("포문으로 멤버를 지웠으므로 member도 널", found);

    }

    public Company createCompany() {
        Address address = Address.createAddress("city", "street", "zipcode");
        Company company = Company.createCompany("companyA", address);

        return company;
    }

    public CompanyDTO createCompanyDTO() {
        Address address = Address.createAddress("city2", "street2", "zipcode2");
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("companyB");
        companyDTO.setStreet("street2");
        companyDTO.setZipcode("zipcode2");

        return companyDTO;
    }

    public Member createMember() {
        Address address = Address.createAddress("city", "street", "zipcode");
        Company company = Company.createCompany("companyA", address);
        Team team = Team.createTeam("teamA", "test", company);

        MemberStatus status = MemberStatus.WORK;
        Member member = Member.createMember("june", 1, "1", address, status, team);

        return member;
    }
}