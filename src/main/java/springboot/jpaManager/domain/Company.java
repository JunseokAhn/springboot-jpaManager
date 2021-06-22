package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.jpaManager.dto.CompanyDTO;

import javax.persistence.*;

import java.util.List;
import java.util.ArrayList;

import static springboot.jpaManager.api.CompanyApiController.*;

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

    public static final Company createCompany(String name, Address address) {
        Company company = new Company();
        company.name = name;
        company.address = address;
        return company;
    }

    public static final Company createCompany(CompanyRequest companyRequest) {

        Company company = new Company();
        company.name = companyRequest.getName();
        company.address = companyRequest.getAddress().transEntity();

        return company;
    }

    public void addTeam(Team team) {
        this.teamList.add(team);
    }

    public void deleteTeam(Team team){
        this.teamList.remove(team);
    }

    public void update(CompanyDTO.UpdateAll companyDTO) {

        this.name = companyDTO.getName();
        this.address = companyDTO.getAddress().transEntity();
    }
}
