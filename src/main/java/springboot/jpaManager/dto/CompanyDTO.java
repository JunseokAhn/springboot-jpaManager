package springboot.jpaManager.dto;

import lombok.Getter;
import lombok.Setter;
import springboot.jpaManager.domain.Company;

@Getter @Setter
public class CompanyDTO {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public static CompanyDTO CreateCompanyDTO(Company company){
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.id = company.getId();
        companyDTO.name = company.getName();
        companyDTO.city = company.getAddress().getCity();
        companyDTO.street = company.getAddress().getStreet();
        companyDTO.zipcode = company.getAddress().getZipcode();
        return companyDTO;
    }
}
