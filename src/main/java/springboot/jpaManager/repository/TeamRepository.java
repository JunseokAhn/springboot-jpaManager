package springboot.jpaManager.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;

    public Long save(Team team) {
        em.persist(team);
        return team.getId();
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public Team findOne(Long teamId) {
        return em.find(Team.class, teamId);
    }

    public List<Team> findAll_v1() {
        return em.createQuery(
                "select distinct t from Team t " +
                        "join fetch t.company c " +
                        "join fetch t.memberList m",
                Team.class).getResultList();
    }

    public List<Team> findAll_v2() {
        return em.createQuery(
                "select distinct t from Team t " +
                        "join fetch t.company c ",
                Team.class).getResultList();
    }

    public List<Team> findByName(String teamName) {
        return em.createQuery("select t from Team t where t.name =:name", Team.class)
                .setParameter("name", teamName).getResultList();
    }


    public void flush() {
        em.flush();
    }


}
