package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.jpaManager.dto.TeamDTO;
import springboot.jpaManager.service.CompanyService;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
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

    public static final Team createTeam(String name, String task, Company company) {
        Team team = new Team();

        team.name = name;
        team.task = task;
        team.company = company;
        company.addTeam(team);

        return team;
    }

    public void addMember(Member member) {
        member.changeTeam(this);
        this.memberList.add(member);
    }

    public void removeMember(Member member) {
        memberList.remove(member);
    }

    public void changeTeamMemberWait() {
        for (Member member : memberList) {
            member.changeStatus(MemberStatus.WAIT);
        }
    }

    public void updateTeam(Team team) {
        this.id = team.id;
        this.name = team.name;
        this.task = team.task;
        this.company.deleteTeam(this);
        this.company = team.company;
        this.company.addTeam(this);
    }

    public int getMemberCount(){
        return memberList.size();
    }
}
