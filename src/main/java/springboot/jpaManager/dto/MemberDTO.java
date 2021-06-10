package springboot.jpaManager.dto;

import lombok.Data;
import springboot.jpaManager.domain.Address;
import springboot.jpaManager.domain.MemberStatus;

@Data
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
    Long companyId;

}
