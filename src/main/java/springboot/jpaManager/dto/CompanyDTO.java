package springboot.jpaManager.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.api.CompanyApiController;
import springboot.jpaManager.domain.Company;

import java.util.List;
import java.util.stream.Collectors;

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

    public Company createEntity(CompanyDTO company) {

        return Company.builder()
                .name(company.getName())
                .address(company.getAddress().createEntity())
                .build();
    }
    public static CompanyDTO createDTO(Company company) {

        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress().createDTO())
                .build();
    }

    public static List<CompanyDTO> transDTOList(List<Company> companyList) {

        List<CompanyDTO> companyDTOList = companyList.stream()
                .map(CompanyDTO::new)
                .collect(Collectors.toList());

        return companyDTOList;
    }


    @Data
    @Builder
    @NoArgsConstructor
    public static class UpdateAll {
        private Long id;
        private String name;
        private AddressDTO address;

        public static UpdateAll createDTO(Long id, CompanyApiController.UpdateCompanyRequest request) {

            return CompanyDTO.UpdateAll.builder()
                    .id(id)
                    .name(request.getName())
                    .address(request.getAddress())
                    .build();
        }

    }

}