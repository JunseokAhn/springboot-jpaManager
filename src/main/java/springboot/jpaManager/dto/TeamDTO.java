package springboot.jpaManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long id;
    private Long companyId;
    private String name;
    private String task;
    private String companyName;
    private int memberCount;


    @Data
    @AllArgsConstructor
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


