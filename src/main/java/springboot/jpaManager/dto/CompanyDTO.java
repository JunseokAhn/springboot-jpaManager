package springboot.jpaManager.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Company;

@Data
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

    @Builder
    public CompanyDTO(Long id, String name, AddressDTO address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Data
    @NoArgsConstructor
    public static class UpdateAll{
        private Long id;
        private String name;
        private AddressDTO address;

        @Builder
        public UpdateAll(Long id, String name, AddressDTO address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }
    }

}