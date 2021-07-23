package springboot.jpaManager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.*;
import springboot.jpaManager.dto.AddressDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;

import java.util.List;

import static org.junit.Assert.*;

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
    ModelMapper modelMapper;

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
        assertEquals("멤버id로 찾아온값이 세이브하기전 member와 같아야 함",member2.getName(), member.getName());

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


    public Company createCompany() {

        return modelMapper.map(createCompanyDTO(), Company.class);
    }

    private CompanyDTO createCompanyDTO() {

        CompanyDTO company = new CompanyDTO();
        company.setName("name");
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


}