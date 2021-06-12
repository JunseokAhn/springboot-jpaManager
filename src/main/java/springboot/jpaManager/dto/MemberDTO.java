package springboot.jpaManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.MemberStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    Long id;
    String name;
    Integer salary;
    String rank;
    String city;
    String street;
    String zipcode;
    MemberStatus status;
    Long teamId;
    String teamName;
    Long companyId;
    String companyName;

}
