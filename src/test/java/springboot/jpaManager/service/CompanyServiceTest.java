package springboot.jpaManager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.MemberStatus;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.AddressDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.repository.entityManager.EmCompanyRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class CompanyServiceTest {

    @Autowired
    CompanyService companyService;
    @Autowired
    EmCompanyRepository companyRepository;
    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;
    @Autowired
    Utils utils;

    @Test
    public void saveCompany() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);

        //when
        Company company2 = companyService.findOne(companyId);
        Company company3 = utils.map(company, Company.class);

        //then
        assertEquals("생성된company와 저장된company는 같다", company3.getName(), company2.getName());

    }

    @Test
    public void updateCompany() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);

        //when
        CompanyDTO.UpdateAll company2 = createCompanyDTOUpdateAll();
        company2.setId(companyId);
        companyService.updateCompany(company2);

        //then
        Company company3 = companyService.findOne(companyId);
        assertEquals("업데이트된 company3의 내용은 업데이트dto와 같다", company3.getName(), company2.getName());

    }


    @Test
    public void deleteCompany() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);

        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);

        MemberDTO member = createMemberDTO(teamId);
        Long memberId = memberService.saveMember(member);

        companyService.flush();

        //when
        companyService.deleteCompany(companyId);

        //then
        Company company2 = companyService.findOne(companyId);
        Team team2 = teamService.findOne(teamId);
        Member member2 = memberService.findOne(memberId);

        assertNull("컴퍼니를 지웠기때문에 null이떠야함", company2);
        assertNull("캐스캐이딩으로 team도 널이 떠야함", team2);
        assertNull("포문으로 멤버를 지웠으므로 member도 널", member2);

    }

    //쿼리한방에 모든 데이터를 가지고 옴
    @Test
    public void findAll_v2() throws Exception {
        //given
        CompanyDTO company = createCompanyDTO("companyA");
        Long companyId = companyService.saveCompany(company);
        CompanyDTO company2 = createCompanyDTO("companyB");
        Long companyId2 = companyService.saveCompany(company2);

        TeamDTO team = createTeamDTO(companyId, "teamA");
        Long teamId = teamService.saveTeam(team);
        TeamDTO team2 = createTeamDTO(companyId2, "teamB");
        Long teamId2 = teamService.saveTeam(team2);
        TeamDTO team3 = createTeamDTO(companyId2, "teamC");
        Long teamId3 = teamService.saveTeam(team3);

        MemberDTO member = createMemberDTO(teamId);
        Long memberId = memberService.saveMember(member);

        //when
        List<Company> companyList = companyRepository.findAll();
        List<CompanyDTO.TeamList> companyDTOList = utils.map(companyList, CompanyDTO.TeamList.class);

        //then
        for (CompanyDTO.TeamList c : companyDTOList) {
            System.out.println("ref = " + c + "id = " + c.getId());
            System.out.println("name = " + c.getName());
            for (Team teamList : c.getTeamList()) {
                System.out.println("teamName = " + teamList.getName());
            }
        }

    }

    //일대다 매핑은 제외하고 모두 한방에 긁어옴 + 일대 다 매핑수만큼 추가로 쿼리가 나감
    @Test
    public void findAll_v3() throws Exception {
        //given
        CompanyDTO company = createCompanyDTO("companyA");
        Long companyId = companyService.saveCompany(company);
        CompanyDTO company2 = createCompanyDTO("companyB");
        Long companyId2 = companyService.saveCompany(company2);

        TeamDTO team = createTeamDTO(companyId, "teamA");
        Long teamId = teamService.saveTeam(team);
        TeamDTO team2 = createTeamDTO(companyId2, "teamB");
        Long teamId2 = teamService.saveTeam(team2);
        TeamDTO team3 = createTeamDTO(companyId2, "teamC");
        Long teamId3 = teamService.saveTeam(team3);

        MemberDTO member = createMemberDTO(teamId);
        Long memberId = memberService.saveMember(member);

        //when
        List<Company> companyList2 = companyRepository.findAll();
        List<CompanyDTO.TeamList> companyDTOList2 = utils.map(companyList2, CompanyDTO.TeamList.class);

        for (CompanyDTO.TeamList c : companyDTOList2) {
            System.out.println("ref = " + c + "id = " + c.getId());
            System.out.println("name = " + c.getName());
            for (Team teamList : c.getTeamList()) {
                System.out.println("teamName = " + teamList.getName());
            }
        }

    }


    public Company createCompany() {

        return utils.map(createCompanyDTO(), Company.class);
    }

    private CompanyDTO createCompanyDTO() {

        CompanyDTO company = new CompanyDTO();
        company.setName("name");
        company.setAddress(createAddressDTO());

        return company;
    }

    private CompanyDTO createCompanyDTO(String companyName) {

        CompanyDTO company = new CompanyDTO();
        company.setName(companyName);
        company.setAddress(createAddressDTO());

        return company;
    }

    private CompanyDTO.UpdateAll createCompanyDTOUpdateAll() {

        CompanyDTO.UpdateAll company = new CompanyDTO.UpdateAll();
        company.setName("name2");
        company.setAddress(createAddressDTO());

        return company;
    }

    private AddressDTO createAddressDTO() {

        AddressDTO address = new AddressDTO();
        address.setCity("city");
        address.setStreet("street");
        address.setZipcode("zipcode");

        return address;
    }

    private MemberDTO createMemberDTO(Long teamId) {

        MemberDTO member = new MemberDTO();
        member.setName("name");
        member.setSalary(1);
        member.setRank("rank");
        member.setAddress(createAddressDTO());
        member.setStatus(MemberStatus.WAIT);
        member.setTeamId(teamId);

        return member;
    }

    private TeamDTO createTeamDTO(long companyId) {

        TeamDTO team = new TeamDTO();
        team.setName("name");
        team.setTask("task");
        team.setMemberCount(0);
        team.setCompanyId(companyId);

        return team;
    }

    private TeamDTO createTeamDTO(long companyId, String teamName) {

        TeamDTO team = new TeamDTO();
        team.setName(teamName);
        team.setTask("task");
        team.setMemberCount(0);
        team.setCompanyId(companyId);

        return team;
    }

}