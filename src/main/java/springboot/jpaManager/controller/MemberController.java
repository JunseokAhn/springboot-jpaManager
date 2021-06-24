package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.service.CompanyService;
import springboot.jpaManager.service.MemberService;
import springboot.jpaManager.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final CompanyService companyService;
    private final TeamService teamService;
    private final MemberService memberService;

    @GetMapping("register")
    public String memberRegister(Model model){

        List<Company> companyList = companyService.findAll();
        List<Team> teamList = teamService.findAll();

        List<CompanyDTO> companyDTOList = companyList.stream()
                .map(CompanyDTO::new).collect(Collectors.toList());
        List<TeamDTO> teamDTOList = teamList.stream()
                .map(TeamDTO::new).collect(Collectors.toList());

        model.addAttribute("companyList", companyDTOList);
        model.addAttribute("teamList", teamDTOList);
        model.addAttribute("form", new MemberDTO());

        return "member/register";
    }

    @PostMapping("register")
    public String memberRegister2(MemberDTO memberDTO){

        memberService.saveMember(memberDTO);

        return "redirect:/member/list";
    }

    @GetMapping("list")
    public String memberList(Model model){

        List<Member> memberList = memberService.findAll();
        List<MemberDTO> memberDTOList = memberService.transDTOList(memberList);
        model.addAttribute("memberList", memberDTOList);


        return "member/list";
    }
}
