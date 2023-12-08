package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.repository.MemberRepository;
import springboot.jpaManager.repository.TeamRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final Utils utils;

    @Transactional
    public Long saveMember(MemberDTO memberDTO) {

        Team team = teamRepository.findById(memberDTO.getTeamId())
                .orElseThrow(() -> new NoSuchElementException("team not found"));

        Member member = Member.createMember(memberDTO, team);
        return utils.isNotReflected(memberRepository.save(member).getId());
    }

    @Transactional
    public void updateMember(Long memberId, Member member) {
        Member origin = this.findOne(memberId);
        origin.updateMember(member);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = this.findOne(memberId);
        memberRepository.delete(member);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("member not found"));
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void flush() {
        memberRepository.flush();
    }

    public List<Member> findByTeam(long teamId) {
        return memberRepository.findByTeamId(teamId);
    }
}
