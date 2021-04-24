package springboot.jpaManager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.*;
import springboot.jpaManager.dto.TeamDTO;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class TeamServiceTest {

    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;

    @Test
    public void saveTeam() throws Exception {

        //given
        TeamDTO teamDTO = createTeamDTO();
        Long teamId = teamService.saveTeam(teamDTO);

        //when
        Team found = teamService.findOne(teamId);

        //then
        assertEquals(found, teamDTO);

    }

    @Test
    public void updateTeam() throws Exception {

        //given
        TeamDTO origin = createTeamDTO();
        Long teamId = teamService.saveTeam(origin);
        teamService.flush();

        //when
        TeamDTO teamDTO = createTeamDTO2();
        teamService.updateTeam(teamDTO);

        //then
        Team fresh = teamService.findOne(teamId);
        assertEquals(fresh.getName(), teamDTO.getName());

    }

    @Test
    public void deleteTeam() throws Exception {

        //given
        TeamDTO teamDTO = createTeamDTO();
        Long teamId = teamService.saveTeam(teamDTO);
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
        TeamDTO teamDTO = createTeamDTO();
        Long teamId = teamService.saveTeam(teamDTO);
        teamService.flush();

        Member member = createMember();
        Long memberId = memberService.saveMember(member);
        memberService.flush();

        teamService.addMember(teamId, memberId);

        //when
        teamService.deleteTeam(teamId);

        //then
        Team found = teamService.findOne(teamId);
        assertNull(found);
        assertEquals("팀을 삭제하면, 팀원의 상태는 대기중으로 바뀐다", MemberStatus.WAIT, member.getStatus());
    }

    @Test
    public void addMember() throws Exception {

        //given
        Member member = createMember();
        Long memberId = memberService.saveMember(member);
        memberService.flush();

        Team teamDTO = createTeamDTO();
        Long teamId = teamService.saveTeam(teamDTO);
        teamService.flush();

        //when
        teamService.addMember(teamId, memberId);

        //then
        Team foundTeam = teamService.findOne(teamId);
        Member foundMember = memberService.findOne(memberId);

        assertEquals("팀의 멤버리스트는 추가한 멤버가 들어감", foundTeam.getMemberList().get(0), foundMember);
        assertEquals("멤버의 팀에는 추가한 팀이 들어감", foundMember.getTeam(), foundTeam);
    }

    public TeamDTO createTeamDTO() {
        Long companyId = 1l;
        String name = "teamA";
        String task = "taskA";
        String companyName = "companyA";
        int memberCount = 0;

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setCompanyId(companyId);
        teamDTO.setName(name);
        teamDTO.setTask(task);
        teamDTO.setCompanyName(companyName);
        teamDTO.setMemberCount(memberCount);

        return teamDTO;
    }
    public TeamDTO createTeamDTO2() {
        Long companyId = 2l;
        String name = "teamB";
        String task = "taskB";
        String companyName = "companyB";
        int memberCount = 0;

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setCompanyId(companyId);
        teamDTO.setName(name);
        teamDTO.setTask(task);
        teamDTO.setCompanyName(companyName);
        teamDTO.setMemberCount(memberCount);

        return teamDTO;
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