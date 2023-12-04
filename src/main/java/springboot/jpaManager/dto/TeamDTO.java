package springboot.jpaManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamDTO {

    private Long id;
    private String name;
    private String task;
    private int memberCount;
    private Long companyId;

    @Data
    @NoArgsConstructor
    public static class List{

        private Long id;
        private String name;
        private String task;
        private Long companyId;
        private String companyName;
        private int memberCount;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Update {

        private Long id;
        private String name;
        private String task;
        private Long companyId;
        private String companyName;

    }
}


