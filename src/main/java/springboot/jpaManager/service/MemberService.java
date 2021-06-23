package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Address;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.MemberStatus;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamService teamService;

    @Transactional
    public Long saveMember(MemberDTO memberDTO) {

        Member member = createEntity(memberDTO);
        return memberRepository.save(member);
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

    private Member createEntity(MemberDTO memberDTO) {

        int salary = 3000000;
        if (memberDTO.getSalary() != null)
            salary = memberDTO.getSalary();

        String rank = "신입";
        if (memberDTO.getRank() == "")
            rank = memberDTO.getRank();

        MemberStatus status = MemberStatus.WAIT;

        String name = memberDTO.getName();
        Address address = memberDTO.getAddress().createEntity();

        Team team = teamService.findOne(memberDTO.getTeamId());
        Member member = Member.createMember(name, salary, rank, address, status, team);

        return member;
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
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

    public List<MemberDTO> transDTOList(List<Member> memberList) {

        List<MemberDTO> memberDTOList = memberList.stream()
                .map(MemberDTO::new).collect(Collectors.toList());

        return memberDTOList;
    }

}
