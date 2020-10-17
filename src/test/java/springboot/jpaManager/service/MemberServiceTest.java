package springboot.jpaManager.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.*;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    TeamService teamService;

    @Test
    public void saveMember() throws Exception {

        //given
        Member member = createMember();

        //when
        Long memberId = memberService.saveMember(member);

        //then
        assertEquals("멤버id로 찾아온값이 세이브하기전 member와 같아야 함",member, memberService.findOne(memberId));

    }

    @Test
    public void updateMember() throws Exception {

        //given
        Member origin = createMember();
        Long memberId = memberService.saveMember(origin);

        //when
        Member member = createMember2();
        memberService.updateMember(memberId, member);
        memberService.flush();

        //then
        Member fresh = memberService.findOne(memberId);
        assertEquals("업데이트된 멤버의 name은 origin과 같다", origin.getName(), member.getName());

    }
    
    @Test
    public void deleteMember() throws Exception {
       
        //given
        Member member = createMember();
        Long memberId = memberService.saveMember(member);

        //when
        memberService.flush();
        memberService.deleteMember(memberId);
        memberService.flush();
        
        //then
        Member found = memberService.findOne(memberId);
        assertNull("삭제된 데이터이므로 null이 나와야함", found);

    }
    
    @Test
    public void findByName() throws Exception {

        //given
        Member member = createMember();
        memberService.saveMember(member);

        //when
        List<Member> memberList = memberService.findByName(member.getName());

        //then
        assertEquals(member, memberList.get(0));

    }

    public Member createMember() {
        Address address = Address.createAddress("city", "street", "zipcode");
        Company company = Company.createCompany("companyA", address);
        Team team = Team.createTeam("teamA", "test", company);

        MemberStatus status = MemberStatus.WORK;
        Member member = Member.createMember("june", 1, "1", address, status, team);

        return member;
    }
    public Member createMember2() {
        Address address = Address.createAddress("city2", "street2", "zipcode2");
        Company company = Company.createCompany("companyB", address);
        Team team = Team.createTeam("teamB", "test2", company);

        MemberStatus status = MemberStatus.WORK;
        Member member = Member.createMember("june2", 2, "2", address, status, team);

        return member;
    }
}