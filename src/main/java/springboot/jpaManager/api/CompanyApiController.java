package springboot.jpaManager.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.service.CompanyService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class CompanyApiController {

    private final CompanyService companyService;

    @PostMapping("create")
    public CompanyResponse createCompany(@RequestBody @Valid CompanyRequest request) {

        Company company = Company.createCompany(request);
        CompanyDTO companyDTO = companyService.transDTO(company);
        Long id = companyService.saveCompany(companyDTO);
        return new CompanyResponse(id);
    }

    @PutMapping("update/{id}")
    public UpdateCompanyResponse updateCompany(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateCompanyRequest request) {

        Company company = Company.createCompany(id, request);
        CompanyDTO companyDTO = companyService.transDTO(company);
        companyService.updateCompany(companyDTO);

        Company updated = companyService.findOne(id);

        return new UpdateCompanyResponse(updated);
    }

    @GetMapping("list")
    public CompanyListResponse listCompany() {
        List<Company> companyList = companyService.findAll();
        List<CompanyDTO> companyDTOList = companyService.transDTOList(companyList);
        return new CompanyListResponse(companyDTOList.size(), companyDTOList);
    }

    @Data
    @AllArgsConstructor
    static class CompanyListResponse<T> {
        private int count;
        private T data;
    }

    @Data
    static class CompanyResponse {
        private Long id;

        public CompanyResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    public static class CompanyRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String city;
        @NotEmpty
        private String street;
        @NotEmpty
        private String zipcode;
    }

    @Data
    static class UpdateCompanyResponse {
        private Long id;
        private String name;
        private String city;
        private String street;
        private String zipcode;

        UpdateCompanyResponse(Company company) {
            this.id = company.getId();
            this.name = company.getName();
            this.city = company.getAddress().getCity();
            this.street = company.getAddress().getStreet();
            this.zipcode = company.getAddress().getZipcode();
        }
    }

    @Data
    public static class UpdateCompanyRequest {
        private String name;
        private String city;
        private String street;
        private String zipcode;
    }

}