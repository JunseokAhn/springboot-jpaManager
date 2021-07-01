package springboot.jpaManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Team;

@Data
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
    @NoArgsConstructor
    public static class UpdateAll {
        private Long id;
        private String name;
        private AddressDTO address;

    }

    @Data
    @NoArgsConstructor
    public static class TeamList {

        private Long id;
        private String name;
        private java.util.List<Team> teamList;

        public TeamList(Company company) {
            this.id = company.getId();
            this.name = company.getName();
            this.teamList = company.getTeamList();
        }
    }

}