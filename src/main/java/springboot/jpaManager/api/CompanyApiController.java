package springboot.jpaManager.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.jpaManager.domain.Address;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.service.CompanyService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CompanyApiController {

    private final CompanyService companyService;

    @PostMapping("create_company")
    public CreateCompanyResponse createCompanyResponse(@RequestBody @Valid CreateCompanyRequest request) {

        String name = request.getName();
        String city = request.getCity();
        String street = request.getStreet();
        String zipcode = request.getZipcode();

        Company company = Company.createCompany(name, city, street, zipcode);
        CompanyDTO companyDTO = companyService.createCompanyDTO(company);
        Long id = companyService.saveCompany(companyDTO);
        return new CreateCompanyResponse(id);
    }

    @Data
    static class CreateCompanyResponse {
        private Long id;

        public CreateCompanyResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateCompanyRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String city;
        @NotEmpty
        private String street;
        @NotEmpty
        private String zipcode;
    }
}
