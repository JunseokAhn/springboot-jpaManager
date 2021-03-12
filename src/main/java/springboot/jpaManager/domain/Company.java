package springboot.jpaManager.domain;

import lombok.AccessLevel;
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

    public static Company createCompany(String name, Address address) {
        Company company = new Company();
        company.name = name;
        company.address = address;
        return company;
    }

    public static Company createCompany(String name, String city, String street, String zipcode) {
        Company company = new Company();
        Address address = Address.createAddress(city, street, zipcode);

        company.name = name;
        company.address = address;
        return company;
    }

    public static Company createCompany(CompanyRequest companyRequest) {
        Company company = new Company();

        String name = companyRequest.getName();
        String city = companyRequest.getCity();
        String street = companyRequest.getStreet();
        String zipcode = companyRequest.getZipcode();
        Address address = Address.createAddress(city, street, zipcode);

        company.name = name;
        company.address = address;
        return company;
    }

    public static Company createCompany(Long id, UpdateCompanyRequest updateCompanyRequest) {
        Company company = new Company();

        String name = updateCompanyRequest.getName();
        String city = updateCompanyRequest.getCity();
        String street = updateCompanyRequest.getStreet();
        String zipcode = updateCompanyRequest.getZipcode();
        Address address = Address.createAddress(city, street, zipcode);

        company.id = id;
        company.name = name;
        company.address = address;
        return company;
    }

    public void addTeam(Team team) {
        this.teamList.add(team);
    }

    public void deleteTeam(Team team){
        this.teamList.remove(team);
    }

    public void update(CompanyDTO companyDTO) {

        String city = companyDTO.getCity();
        String street = companyDTO.getStreet();
        String zipcode = companyDTO.getZipcode();
        Address address = Address.createAddress(city, street, zipcode);

        this.name = companyDTO.getName();
        this.address = address;
    }
}
