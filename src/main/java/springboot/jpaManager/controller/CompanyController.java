package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.service.CompanyService;

import java.util.List;

@Controller
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("register")
    public String companyRegister(Model model) {

        model.addAttribute("form", new CompanyDTO());
        return "company/register";
    }

    @PostMapping("register")
    public String companyRegister2(CompanyDTO companyDTO) {
        companyService.saveCompany(companyDTO);
        return "company/register";
    }

    @GetMapping("list")
    public String companyList(Model model) {
        List<Company> companyList = companyService.findAll();
        model.addAttribute("company_list", companyList);
        return "company/list";
    }
}
