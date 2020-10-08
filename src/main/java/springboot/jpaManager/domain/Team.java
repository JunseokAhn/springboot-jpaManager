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

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> memberList = new ArrayList<>();

    public Team createTeam(Long id, String name, String task, Company company) {
        this.id = id;
        this.name = name;
        this.task = task;
        this.company = company;
        company.addTeam(this);

        return this;
    }

    public void addMember(Member member) {
        this.memberList.add(member);
    }
}
