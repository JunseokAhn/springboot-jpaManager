package springboot.jpaManager.domain;

import lombok.Getter;

import javax.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
public class Company {

    @GeneratedValue
    @Column(name = "COMPANY_ID")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Team> teamList = new ArrayList<>();
}
