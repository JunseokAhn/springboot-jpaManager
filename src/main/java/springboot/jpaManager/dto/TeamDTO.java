package springboot.jpaManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Team;

@Data
@NoArgsConstructor
public class TeamDTO {

    private Long id;
    private Long companyId;
    private String name;
    private String task;
    private String companyName;
    private int memberCount;

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.companyId = team.getCompany().getId();
        this.name = team.getName();
        this.task = team.getTask();
        this.companyName = team.getCompany().getName();
        this.memberCount = team.getMemberCount();
    }

    @Data
    @NoArgsConstructor
    public static class UpdateAll {

        private Long id;
        private Long companyId;
        private String name;
        private String task;
        private String companyName;
        private int memberCount;


    }
}


