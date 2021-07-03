package springboot.jpaManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.MemberStatus;

@Data
@NoArgsConstructor
public class MemberDTO {

    Long id;
    String name;
    Integer salary;
    String rank;
    AddressDTO address;
    MemberStatus status;
    Long teamId;

    @Data
    @NoArgsConstructor
    public static class List {

        Long id;
        String name;
        Integer salary;
        String rank;
        MemberStatus status;
        String teamName;
        String companyName;

    }
}
