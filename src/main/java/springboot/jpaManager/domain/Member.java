package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.jpaManager.dto.MemberDTO;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;
    private int salary;
    private String rank;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public static Member createMember(MemberDTO memberDTO, Team team) {
        Member member = new Member();
        member.name = memberDTO.getName();
        member.salary = memberDTO.getSalary();
        member.rank = member.getRank();
        member.address = memberDTO.getAddress().createEntity();
        member.status = memberDTO.getStatus();
        member.team = team;
        team.addMember(member);

        return member;
    }

    public void changeStatus(MemberStatus status) {
        this.status = status;
    }

    public void updateMember(Member member) {
        this.name = member.name;
        this.salary = member.salary;
        this.rank = member.rank;
        this.address = member.address;
        this.status = member.status;
    }

    public void changeTeam(Team team) {
        team.removeMember(this);
        this.team = team;
    }
}
