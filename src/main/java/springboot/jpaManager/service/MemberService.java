package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.repository.JpqlMemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final JpqlMemberRepository memberRepository;
    private final TeamService teamService;
    private final Utils utils;

    @Transactional
    public Long saveMember(MemberDTO memberDTO) {

        Team team = teamService.findOne(memberDTO.getTeamId());
        Member member = Member.createMember(memberDTO, team);
        return utils.isNotReflected(memberRepository.save(member));
    }

    @Transactional
    public void updateMember(Long memberId, Member member) {
        Member origin = memberRepository.findOne(memberId);
        origin.updateMember(member);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        memberRepository.delete(member);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public List<Member> findAll() {
        return memberRepository.findAll_fetchJoin();
    }

    public void flush() {
        memberRepository.flush();
    }

}
