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

    @Id
    @GeneratedValue
    @Column(name = "COMPANY_ID")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<Team> teamList = new ArrayList<>();

    public static Company createCompany(String name, Address address) {
        Company company = new Company();
        company.name = name;
        company.address = address;
        return company;
    }
    public void addTeam(Team team) {
        this.teamList.add(team);
    }

    public void deleteTeam(Team team){
        //팀 해체시 사용
//        team.changeTeamMember();
        this.teamList.remove(team);
    }

    public void update(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
