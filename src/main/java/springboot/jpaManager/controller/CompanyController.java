package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.service.CompanyService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

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
        List<CompanyDTO> companyList2 = companyList.stream().map(
                company -> modelMapper.map(company, CompanyDTO.class)
        ).collect(Collectors.toList());

        model.addAttribute("company_list", companyList2);
        return "company/list";
    }

    @GetMapping("edit/{companyId}")
    public String companyEdit(@PathVariable("companyId") Long companyId, Model model) {
        Company company = companyService.findOne(companyId);
        CompanyDTO companyDTO = modelMapper.map(company, CompanyDTO.class);
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
