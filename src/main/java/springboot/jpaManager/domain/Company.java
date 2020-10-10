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
public class Company {

    @GeneratedValue
    @Column(name = "COMPANY_ID")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<Team> teamList = new ArrayList<>();

    public Company createCompany(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;

        return this;
    }

    public void addTeam(Team team) {
        this.teamList.add(team);
    }

    public void deleteTeam(Team team){
        team.changeTeamMember();
        this.teamList.remove(team);
    }
}
