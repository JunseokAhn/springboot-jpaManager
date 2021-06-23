package springboot.jpaManager.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.AddressDTO;
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

        Company company = Company.builder()
                .name(request.getName())
                .address(request.getAddress().createEntity())
                .build();

        CompanyDTO companyDTO = CompanyDTO.createDTO(company);
        Long id = companyService.saveCompany(companyDTO);
        return new CompanyResponse(id);
    }

    @PutMapping("update/{id}")
    public UpdateCompanyResponse updateCompany(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateCompanyRequest request) {

        CompanyDTO.UpdateAll companyDTO = CompanyDTO.UpdateAll.createDTO(id, request);
        companyService.updateCompany(companyDTO);
        Company updated = companyService.findOne(id);

        return new UpdateCompanyResponse(updated);
    }

    @GetMapping("list")
    public CompanyListResponse listCompany() {
        List<Company> companyList = companyService.findAll();
        List<CompanyDTO> companyDTOList = CompanyDTO.transDTOList(companyList);
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
        @Valid
        private AddressDTO address;
    }

    @Data
    static class UpdateCompanyResponse {
        private Long id;
        private String name;
        private AddressDTO address;

        UpdateCompanyResponse(Company company) {
            this.id = company.getId();
            this.name = company.getName();
            this.address = company.getAddress().createDTO();
        }
    }

    @Data
    public static class UpdateCompanyRequest {
        private String name;
        private AddressDTO address;
    }

}
