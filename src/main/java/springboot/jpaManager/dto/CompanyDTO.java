package springboot.jpaManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Company;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private Long id;
    private String name;
    private AddressDTO address;

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress().transDTO();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateAll{
        private Long id;
        private String name;
        private AddressDTO address;
    }

}