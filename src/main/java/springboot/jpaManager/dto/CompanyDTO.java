package springboot.jpaManager.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Company;

@Data
@Builder
@NoArgsConstructor
public class CompanyDTO {

    private Long id;
    private String name;
    private AddressDTO address;

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress().createDTO();
    }

    @Data
    @Builder
    @NoArgsConstructor
    public static class UpdateAll {
        private Long id;
        private String name;
        private AddressDTO address;

    }

}