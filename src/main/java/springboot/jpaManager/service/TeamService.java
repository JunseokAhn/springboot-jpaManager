package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.CompanyDTO;
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

    @Transactional
    public Long saveTeam(TeamDTO teamDTO) {
        Team team = transEntity(teamDTO);
        return teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(TeamDTO teamDTO) {
        Team origin = findOne(teamDTO.getId());
        Company company = companyService.findOne(teamDTO.getCompanyId());

        origin.updateTeam(teamDTO,company);
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

    public Team transEntity(TeamDTO teamDTO) {

        String name = teamDTO.getName();
        String task = teamDTO.getTask();
        Long companyId = teamDTO.getCompanyId();
        Company company = companyService.findOne(companyId);

        return Team.createTeam(name, task, company);
    }

    public List<TeamDTO> transDTOList(List<Team> teamList){

        List<TeamDTO> teamDTOList = teamList.stream().map(m -> new TeamDTO
                (m.getId(), m.getCompany().getId(), m.getName(), m.getTask(), m.getCompany().getName(), m.getMemberCount())
        ).collect(Collectors.toList());

        return teamDTOList;
    }

    public TeamDTO transDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setTask(team.getTask());
        Long companyId = team.getCompany().getId();
        teamDTO.setMemberCount(team.getMemberCount());

        teamDTO.setCompanyId(companyId);
        Company company = companyService.findOne(companyId);
        teamDTO.setCompanyName(company.getName());

        return teamDTO;
    }
}
