package springboot.jpaManager.repository.entityManager;

import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmTeamRepository extends EmRepository<Team> {

    public EmTeamRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Long save(Team team) {
        em.persist(team);
        return team.getId();
    }

    @Override
    public Team findOne(Long teamId) {
        return em.find(Team.class, teamId);
    }


    @Override
    public List<Team> findByName(String teamName) {
        return em.createQuery("select t from Team t where t.name =:name", Team.class)
                .setParameter("name", teamName).getResultList();
    }

    @Override
    public List<Team> findAll() {
        return em.createQuery(
                "select distinct t from Team t " +
                        "left outer join fetch t.company c " +
                        "left outer join fetch t.memberList m",
                Team.class).getResultList();
    }


    @Override
    public List<Team> findWithPaging(int start, int end) {
        return em.createQuery(
                        "select distinct t from Team t " +
                                "left outer join fetch t.company c",
                        Team.class)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
    }

}
