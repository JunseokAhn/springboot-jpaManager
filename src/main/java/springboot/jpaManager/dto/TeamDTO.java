package springboot.jpaManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Team;

@Data
@NoArgsConstructor
public class TeamDTO {

    private Long id;
    private String name;
    private String task;
    private int memberCount;
    private Long companyId;

    public TeamDTO(Team team) {

        this.id = team.getId();
        this.name = team.getName();
        this.task = team.getTask();
        this.memberCount = team.getMemberCount();
        this.companyId = team.getCompany().getId();
    }

    @Data
    @NoArgsConstructor
    public static class List{

        private Long id;
        private String name;
        private String task;
        private Long companyId;
        private String companyName;
        private int memberCount;

        public List(Team team) {
            this.id = team.getId();
            this.name = team.getName();
            this.task = team.getTask();
            this.companyId = team.getCompany().getId();
            this.companyName = team.getCompany().getName();
            this.memberCount = team.getMemberCount();
        }
    }

    @Data
    @NoArgsConstructor
    public static class Update {

        private Long id;
        private String name;
        private String task;
        private Long companyId;
        private String companyName;

    }
}


