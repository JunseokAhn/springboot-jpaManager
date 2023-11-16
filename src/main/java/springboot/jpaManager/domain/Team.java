package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.jpaManager.dto.TeamDTO;

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany(mappedBy = "team", cascade = CascadeType.MERGE)
    private List<Member> memberList = new ArrayList<>();

    public static final Team createTeam(TeamDTO teamDTO, Company company) {
        Team team = new Team();

        team.name = teamDTO.getName();
        team.task = teamDTO.getTask();
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

    public void update(TeamDTO.Update teamDTO, Company company) {

        this.id = teamDTO.getId();
        if (teamDTO.getName() != null)
            this.name = teamDTO.getName();
        if (teamDTO.getTask() != null)
            this.task = teamDTO.getTask();
        if (company.getId() != null) {
            this.company.deleteTeam(this);
            this.company = company;
            this.company.addTeam(this);
        }
    }

    public int getMemberCount() {
        return memberList.size();
    }
}
