package springboot.jpaManager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.repository.JpqlMemberRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static springboot.jpaManager.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    CompanyService companyService;
    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;
    @Autowired
    JpqlMemberRepository memberRepository;
    @Autowired
    Utils utils;


    @Test
    public void saveMember() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);
        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);
        MemberDTO member = createMemberDTO(teamId);

        //when
        Long memberId = memberService.saveMember(member);
        Member member2 = memberService.findOne(memberId);

        //then
        assertEquals("멤버id로 찾아온값이 세이브하기전 member와 같아야 함", member2.getName(), member.getName());

    }

    @Test
    public void deleteMember() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);
        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);
        MemberDTO member = createMemberDTO(teamId);
        Long memberId = memberService.saveMember(member);
        memberService.flush();

        //when
        memberService.deleteMember(memberId);

        //then
        Member found = memberService.findOne(memberId);
        assertNull("삭제된 데이터이므로 null이 나와야함", found);

    }

    @Test
    public void findByName() throws Exception {

        //given
        CompanyDTO company = createCompanyDTO();
        Long companyId = companyService.saveCompany(company);
        TeamDTO team = createTeamDTO(companyId);
        Long teamId = teamService.saveTeam(team);
        MemberDTO member = createMemberDTO(teamId);
        Long memberId = memberService.saveMember(member);
        memberService.flush();

        //when
        List<Member> memberList = memberService.findByName(member.getName());
        Member member2 = memberService.findOne(memberId);

        //then
        assertEquals(member2, memberList.get(0));

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
        List<Member> memberList = memberRepository.findAll_noDistinct();

        List<MemberDTO.List> memberDTOList = utils.map(memberList, MemberDTO.List.class);

        //then
        for (MemberDTO.List m : memberDTOList) {
            System.out.println("ref = " + m + "id = " + m.getId());
            System.out.println("name = " + m.getName());
            System.out.println("status = " + m.getStatus());
        }
    }

}