package springboot.jpaManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Member;
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

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.salary = member.getSalary();
        this.rank = member.getRank();
        this.address = member.getAddress().createDTO();
        this.status = member.getStatus();
        this.teamId = member.getTeam().getId();
    }
}
