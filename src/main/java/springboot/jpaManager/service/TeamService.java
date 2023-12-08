package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.repository.TeamRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final CompanyService companyService;
    private final MemberService memberService;
    private final TeamRepository teamRepository;
    private final Utils utils;

    @Transactional
    public Long saveTeam(TeamDTO teamDTO) {
        Company company = companyService.findOne(teamDTO.getCompanyId());
        Team team = Team.createTeam(teamDTO, company);

        return utils.isNotReflected(teamRepository.save(team).getId());
    }

    @Transactional
    public void updateTeam(TeamDTO.Update teamDTO) {
        Team origin = findOne(teamDTO.getId());
        Company company = null;
        if(teamDTO.getCompanyId() != null){
            company = companyService.findOne(teamDTO.getCompanyId());
        }

        origin.update(teamDTO, company);
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = this.findOne(teamId);
        team.changeTeamMemberWait();
        teamRepository.delete(team);
    }

    @Transactional
    public void addMember(Long teamId, Long memberId) {
        Member member = memberService.findOne(memberId);
        Team team = this.findOne(teamId);
        team.addMember(member);
    }

    public Team findOne(Long id) {
        return teamRepository.findTeamWithCompanyById(id)
                .orElseThrow(() -> new NoSuchElementException("team not found"));
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }


}
