package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.jpaManager.dto.CompanyDTO;

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

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Team> teamList = new ArrayList<>();

    public void addTeam(Team team) {
        this.teamList.add(team);
    }

    public void deleteTeam(Team team){
        this.teamList.remove(team);
    }

    public void updateAll(CompanyDTO.UpdateAll companyDTO) {

        this.name = companyDTO.getName();
        this.address = companyDTO.getAddress().createEntity();
    }
}
