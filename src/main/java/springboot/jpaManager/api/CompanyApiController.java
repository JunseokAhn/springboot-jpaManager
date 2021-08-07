package springboot.jpaManager.api;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import springboot.jpaManager.Method;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.CompanyApiDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.service.CompanyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class CompanyApiController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final Method method;

    @PostMapping("create")
    public CompanyApiDTO.Response createCompany(@RequestBody @Valid CompanyApiDTO.Request request) {

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);
        Long id = companyService.saveCompany(company);
        return new CompanyApiDTO.Response(id);
    }

    @PutMapping("update/{id}")
    public CompanyApiDTO.UpdateResponse updateCompany(
            @PathVariable("id") Long id,
            @RequestBody @Valid CompanyApiDTO.UpdateRequest request) {

        CompanyDTO.UpdateAll companyDTO = modelMapper.map(request, CompanyDTO.UpdateAll.class);
        companyService.updateCompany(companyDTO);
        Company updated = companyService.findOne(id);

        return modelMapper.map(updated, CompanyApiDTO.UpdateResponse.class);
    }

    @GetMapping("list")
    public CompanyApiDTO.ListResponse listCompany() {
        List<Company> companyList = companyService.findAll();
        List<CompanyDTO> companyList2 = method.mapList(companyList, CompanyDTO.class);

        return new CompanyApiDTO.ListResponse(companyList2.size(), companyList2);
    }


}
