package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.service.CompanyService;
import springboot.jpaManager.service.TeamService;

import java.util.List;

@Controller
@RequestMapping("team")
@RequiredArgsConstructor
public class TeamController {

    private final CompanyService companyService;
    private final TeamService teamService;

    @GetMapping("register")
    public String teamRegister(Model model) {
        List<Company> companyList = companyService.findAll();
        List<CompanyDTO> companyDTOList = companyService.transDTOList(companyList);
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
    public String teamList() {
        return "redirect:/";
    }
}
