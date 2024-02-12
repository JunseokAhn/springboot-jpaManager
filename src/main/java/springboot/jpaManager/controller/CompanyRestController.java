package springboot.jpaManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.CompanyApiDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.service.CompanyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;
    private final Utils utils;

    @PostMapping("create")
    public CompanyApiDTO.Response createCompany(@RequestBody @Valid CompanyApiDTO.Request request) {

        CompanyDTO company = utils.map(request, CompanyDTO.class);
        Long id = companyService.saveCompany(company);
        return new CompanyApiDTO.Response(id);
    }

    @PutMapping("update/{id}")
    public CompanyApiDTO.UpdateResponse updateCompany(
            @PathVariable("id") Long id,
            @RequestBody @Valid CompanyApiDTO.UpdateRequest request) {

        CompanyDTO.UpdateAll companyDTO = utils.map(request, CompanyDTO.UpdateAll.class);
        companyService.updateCompany(companyDTO);
        Company updated = companyService.findOne(id);

        return utils.map(updated, CompanyApiDTO.UpdateResponse.class);
    }

    @GetMapping("list")
    public CompanyApiDTO.ListResponse listCompany() {
        List<Company> companyList = companyService.findAll();
        List<CompanyDTO> companyList2 = utils.map(companyList, CompanyDTO.class);

        return new CompanyApiDTO.ListResponse(companyList2.size(), companyList2);
    }


}
