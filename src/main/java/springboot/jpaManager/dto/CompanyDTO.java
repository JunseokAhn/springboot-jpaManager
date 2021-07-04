package springboot.jpaManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Team;

@Data
@NoArgsConstructor
public class CompanyDTO {

    private Long id;
    private String name;
    private AddressDTO address;

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

    }

}