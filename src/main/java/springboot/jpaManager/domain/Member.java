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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member createMember(Long id, String name, int salary, String rank,
                               Address address, MemberStatus status, Team team) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.rank = rank;
        this.address = address;
        this.status = status;
        this.team = team;
        team.addMember(this);

        return this;
    }
}
