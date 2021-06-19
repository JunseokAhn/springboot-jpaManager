package springboot.jpaManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.MemberStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    Long id;
    String name;
    Integer salary;
    String rank;
    AddressDTO address;
    MemberStatus status;
    Long teamId;
    String teamName;
    Long companyId;
    String companyName;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.salary = member.getSalary();
        this.rank = member.getRank();
        this.address = member.getAddress().transDTO();
        this.status = member.getStatus();
        this.teamId = member.getTeam().getId();
        this.teamName = member.getTeam().getName();
        this.companyId = member.getTeam().getCompany().getId();
        this.companyName = member.getTeam().getCompany().getName();
    }
}
