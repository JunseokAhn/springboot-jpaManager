package springboot.jpaManager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.Method;
import springboot.jpaManager.domain.*;
import springboot.jpaManager.dto.AddressDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.repository.JpqlTeamRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamServiceTest {

    @Autowired
    CompanyService companyService;
    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JpqlTeamRepository teamRepository;
    @Autowired
    Method method;

    @Test
    public void saveTeam() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);
        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);

        //when
        Team team2 = teamService.findOne(teamId);

        //then
        assertEquals(team2.getName(), team.getName());

    }

    @Test
    public void updateTeam() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);

        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);

        //when
        CompanyDTO company2 = createCompanyDTO();
        Long companyId2 = companyService.saveCompany(company2);
        Team team2 = teamService.findOne(teamId);
        TeamDTO.Update team3 = modelMapper.map(team2, TeamDTO.Update.class);
        team3.setCompanyId(companyId2);

        teamService.updateTeam(team3);

        //then
        Team team4 = teamService.findOne(teamId);
        assertEquals("업데이트 전과 후의 값이 같다", team3.getCompanyName(), team4.getCompany().getName());

    }


    @Test
    public void deleteTeam() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);
        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);
        teamService.flush();

        //when
        teamService.deleteTeam(teamId);

        //then
        Team found = teamService.findOne(teamId);
        assertNull(found);

    }

    @Test
    public void teamMemberWait() throws Exception {
        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);
        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);
        teamService.flush();

        MemberDTO member = createMemberDTO(teamId);
        Long memberId = memberService.saveMember(member);
        memberService.flush();

        teamService.addMember(teamId, memberId);

        //when
        teamService.deleteTeam(teamId);

        //then
        Team team2 = teamService.findOne(teamId);
        assertNull("팀은 삭제된다.", team2);
        assertEquals("팀을 삭제하면, 팀원의 상태는 대기중으로 바뀐다", MemberStatus.WAIT, member.getStatus());
    }


    @Test
    public void addMember() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);
        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);
        MemberDTO member = createMemberDTO(teamId);
        Long memberId = memberService.saveMember(member);
//        memberService.flush();
//        teamService.flush();

        //when
        teamService.addMember(teamId, memberId);

        //then
        Team team2 = teamService.findOne(teamId);
        Member member2 = memberService.findOne(memberId);

        assertEquals("팀의 멤버리스트는 추가한 멤버가 들어감", team2.getMemberList().get(0), member2);
        assertEquals("멤버의 팀에는 추가한 팀이 들어감", member2.getTeam(), team2);
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
        List<Team> teamList = teamRepository.findAll_noDistinct();

        List<TeamDTO.List> TeamDTOList = method.mapList(teamList, TeamDTO.List.class);

        //then
        for (TeamDTO.List m : TeamDTOList) {
            System.out.println("ref = " + m + "id = " + m.getId());
            System.out.println("name = " + m.getName());
        }

    }

    /*
    쿼리 한방에 toOne까지 데이터를 가져온 후,
    toMany컬럼의 데이터를 가져와서 자바에서 조립하는방식
    페이징가능
    */
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
        List<Team> teamList = teamRepository.findAll_paging_inMemory();

        List<TeamDTO.List> TeamDTOList = method.mapList(teamList, TeamDTO.List.class);

        //then
        for (TeamDTO.List m : TeamDTOList) {
            System.out.println("ref = " + m + "id = " + m.getId());
            System.out.println("name = " + m.getName());
        }

    }

    public Company createCompany() {

        return modelMapper.map(createCompanyDTO(), Company.class);
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