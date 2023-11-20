package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.repository.JpqlMemberRepository;
import springboot.jpaManager.repository.JpqlTeamRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final CompanyService companyService;
    private final JpqlTeamRepository teamRepository;
    private final JpqlMemberRepository memberRepository;
    private final Utils utils;

    @Transactional
    public Long saveTeam(TeamDTO teamDTO) {
        Company company = companyService.findOne(teamDTO.getCompanyId());
        Team team = Team.createTeam(teamDTO, company);

        return utils.isNotReflected(teamRepository.save(team));
    }

    @Transactional
    public void updateTeam(TeamDTO.Update teamDTO) {
        Team origin = findOne(teamDTO.getId());
        Company company = companyService.findOne(teamDTO.getCompanyId());

        origin.update(teamDTO, company);
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findOne(teamId);
        team.changeTeamMemberWait();
        teamRepository.delete(team);
    }

    @Transactional
    public void addMember(Long teamId, Long memberId) {
        Member member = memberRepository.findOne(memberId);
        Team team = teamRepository.findOne(teamId);
        team.addMember(member);
    }

    public Team findOne(Long id) {
        return teamRepository.findOne(id);
    }

    public List<Team> findAll() {
        return teamRepository.findAll_fetchJoin();
    }

    public void flush() {
        teamRepository.flush();
    }

}
