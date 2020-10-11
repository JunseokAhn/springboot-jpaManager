package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Member {

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

    public static final Member createMember(Long id, String name, int salary, String rank,
                                            Address address, MemberStatus status, Team team) {
        Member member = new Member();
        member.id = id;
        member.name = name;
        member.salary = salary;
        member.rank = rank;
        member.address = address;
        member.status = status;
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

    public void changeTeam(Member member, Team team) {
        member.team.removeMember(member);
        member.team = team;
        member.team.addMember(member);
    }
}
