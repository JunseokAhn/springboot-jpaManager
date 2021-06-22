package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "redirect:/company/list";
    }

    @GetMapping("list")
    public String companyList(Model model) {
        List<Company> companyList = companyService.findAll();
        List<CompanyDTO> companyDTOList = companyService.transDTOList(companyList);
        model.addAttribute("company_list", companyDTOList);
        return "company/list";
    }

    @GetMapping("edit/{companyId}")
    public String companyEdit(@PathVariable("companyId") Long companyId, Model model) {
        Company company = companyService.findOne(companyId);
        CompanyDTO companyDTO = companyService.transDTO(company);
        model.addAttribute("company", companyDTO);
        return "company/edit";
    }

    @PostMapping("edit")
    public String companyEdit2(CompanyDTO.UpdateAll companyDTO) {
        companyService.updateCompany(companyDTO);
        return "redirect:/company/list";
    }

    @GetMapping("delete/{companyId}")
    public String companyDelete(@PathVariable("companyId") Long companyId) {
        companyService.deleteCompany(companyId);
        return "redirect:/company/list";
    }
}
