package springboot.jpaManager.dto;

import lombok.Data;

@Data
public class TeamDTO {
    private Long companyId;
    private String name;
    private String task;
}
