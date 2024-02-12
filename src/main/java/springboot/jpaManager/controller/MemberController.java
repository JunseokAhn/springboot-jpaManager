package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.service.CompanyService;
import springboot.jpaManager.service.MemberService;

import java.util.List;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final CompanyService companyService;
    private final MemberService memberService;
    private final Utils utils;

    @GetMapping("register")
    public String memberRegister(Model model) {

        List<Company> companyList = companyService.findAll();

        List<CompanyDTO.TeamList> companyList2 = utils.map(companyList, CompanyDTO.TeamList.class);

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

        List<MemberDTO.List> memberDTOList = utils.map(memberList, MemberDTO.List.class);

        model.addAttribute("memberList", memberDTOList);

        return "member/list";
    }

}
