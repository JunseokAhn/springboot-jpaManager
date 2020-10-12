package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;
    private String task;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany(mappedBy = "team", cascade = CascadeType.MERGE)
    private List<Member> memberList = new ArrayList<>();

    public static final Team createTeam(Long id, String name, String task, Company company) {
        Team team = new Team();

        team.id = id;
        team.name = name;
        team.task = task;
        team.company = company;
        company.addTeam(team);

        return team;
    }

    public void addMember(Member member) {
        member.changeTeam(member, this);
        this.memberList.add(member);
    }

    public void removeMember(Member member) {
        memberList.remove(member);
    }

    public void changeTeamMember() {
        for (Member member : memberList) {
            member.changeStatus(MemberStatus.WAIT);
        }
    }

    public void updateTeam(Team origin, Team team) {
        origin.id = team.id;
        origin.name = team.name;
        origin.task = team.task;
        origin.company.deleteTeam(origin);
        origin.company = team.company;
        origin.company.addTeam(origin);
    }
}
