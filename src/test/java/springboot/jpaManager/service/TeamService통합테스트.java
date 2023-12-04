package springboot.jpaManager.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.MemberStatus;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.TeamDTO;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

@SpringBootTest
public class TeamService통합테스트 {

    @Autowired
    private TeamService teamService;
    @Autowired
    private MemberService memberService;

    @Test
    public void updateTeam_success() {

        //when
        TeamDTO.Update update = TeamDTO.Update.builder()
                .id(1L)
                .companyId(1L)
                .name("변경된이름")
                .build();
        teamService.updateTeam(update);

        //then
        Team team2 = teamService.findOne(1L);
        Assertions.assertThat(team2.getName()).isEqualTo("변경된이름");
    }
    @Test
    public void updateTeam_invalidParam_fail() {

        //when
        TeamDTO.Update update = TeamDTO.Update.builder()
                .id(1L)
                .name("변경된이름")
                .build();
        teamService.updateTeam(update);

        //then
        Team team = teamService.findOne(1L);
        Assertions.assertThat(team.getName()).isEqualTo("변경된이름");
    }

    @Test
    @DisplayName("팀을 삭제하면 멤버.status가 WAIT으로 변경")
    public void whenDeleteTeam_thenChangeMemberStatus_WAIT() {
        //when
        teamService.deleteTeam(1L);
        List<Member> members= memberService.findByTeam(1L);

        //then
        members.forEach(member -> Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.WAIT));
    }


    @Test
    @Transactional
    public void addMember_success() {

        //given
        long teamId = 1L;
        long memberId= 4L;
        assertNotEquals( "memberId 4L은 teamId 1L의 멤버가 아니다",teamId, memberService.findOne(memberId).getTeam().getId());

        //when
        teamService.addMember(teamId, memberId);

        //then
        Team team = teamService.findOne(teamId);
        Member member = memberService.findOne(memberId);

        assertEquals("멤버의 팀에는 추가한 팀이 들어간다", member.getTeam(), team);
    }
}