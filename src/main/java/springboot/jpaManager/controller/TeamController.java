package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.service.CompanyService;
import springboot.jpaManager.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("team")
@RequiredArgsConstructor
public class TeamController {

    private final CompanyService companyService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @GetMapping("register")
    public String teamRegister(Model model) {
        List<Company> companyList = companyService.findAll();
        List<CompanyDTO> companyDTOList = companyList.stream()
                .map(CompanyDTO::new).collect(Collectors.toList());

        model.addAttribute("form", new TeamDTO());
        model.addAttribute("companyList", companyDTOList);

        return "team/register";
    }

    @PostMapping("register")
    public String teamRegister2(TeamDTO teamDTO) {
        System.out.println(teamDTO);
        teamService.saveTeam(teamDTO);
        return "redirect:/team/list";
    }

    @GetMapping("list")
    public String teamList(Model model) {
        List<Team> teamList = teamService.findAll();
        List<TeamDTO> teamDTOList = teamList.stream()
                .map(TeamDTO::new).collect(Collectors.toList());

        model.addAttribute("teamDTOList", teamDTOList);
        return "team/list";
    }

    @GetMapping("teamManage/{teamId}")
    public String teamManage(@PathVariable("teamId") Long teamId, Model model){

        Team team = teamService.findOne(teamId);
        TeamDTO.UpdateAll teamDTO = modelMapper.map(team, TeamDTO.UpdateAll.class);
        model.addAttribute("teamDTO", teamDTO);

        return "team/teamManage";
    }

    @PostMapping("teamManage")
    public String teamManage2(TeamDTO.UpdateAll teamDTO){

        teamService.updateTeam(teamDTO);
        return "redirect:/team/list";
    }

    @GetMapping("memberManage/{teamId}")
    public String memberManage(@PathVariable("teamId") Long teamId, Model model){
        //findMemberAll using teamId
        //need Member register
        return "team/memberManage";
    }
}
