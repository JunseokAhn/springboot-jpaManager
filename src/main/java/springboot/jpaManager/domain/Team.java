package springboot.jpaManager.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
public class Team {

    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;
    private String task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> memberList = new ArrayList<>();
}
