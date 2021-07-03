package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.service.CompanyService;
import springboot.jpaManager.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final CompanyService companyService;
    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @GetMapping("register")
    public String memberRegister(Model model) {

        List<Company> companyList = companyService.findAll();

        List<CompanyDTO.TeamList> companyList2 = companyList.stream().map(
                company -> modelMapper.map( company, CompanyDTO.TeamList.class)
        ).collect(Collectors.toList());

        model.addAttribute("companyList", companyList2);
        model.addAttribute("form", new MemberDTO());

        return "member/register";
    }

    @PostMapping("register")
    public String memberRegister2(MemberDTO memberDTO) {
        memberService.saveMember(memberDTO);

        return "redirect:/member/list";
    }

    @GetMapping("list")
    public String memberList(Model model) {

        List<Member> memberList = memberService.findAll();

        modelMapper.typeMap(Member.class, MemberDTO.List.class).addMappings( mapper -> {
            mapper.map( Member -> Member.getTeam().getName(), MemberDTO.List::setTeamName);
            mapper.map( Member -> Member.getTeam().getCompany().getName(), MemberDTO.List::setCompanyName);
        });

        List<MemberDTO.List> memberDTOList = memberList.stream().map(
                member -> modelMapper.map( member, MemberDTO.List.class)
        ).collect(Collectors.toList());

        model.addAttribute("memberList", memberDTOList);

        return "member/list";
    }

}
