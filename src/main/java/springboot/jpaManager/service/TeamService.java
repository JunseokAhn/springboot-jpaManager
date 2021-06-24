package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.repository.MemberRepository;
import springboot.jpaManager.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final CompanyService companyService;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long saveTeam(TeamDTO teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);
        return teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(TeamDTO.UpdateAll teamDTO) {
        Team origin = findOne(teamDTO.getId());
        Company company = companyService.findOne(teamDTO.getCompanyId());

        origin.updateAll(teamDTO,company);
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
        return teamRepository.findAll();
    }

    public void flush() {
        teamRepository.flush();
    }

}
