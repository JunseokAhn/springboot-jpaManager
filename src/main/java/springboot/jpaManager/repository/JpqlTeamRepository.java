package springboot.jpaManager.repository;

import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class JpqlTeamRepository extends JpqlRepository<Team>{

    public JpqlTeamRepository(EntityManager em){
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
    public List<Team> findAll_noOption() {
        return em.createQuery(
                "select t from Team t " +
                        "join t.company c " +
                        "join t.memberList m",
                Team.class).getResultList();
    }

    @Override
    List<Team> findAll_distinct() {
        return em.createQuery(
                "select distinct t from Team t " +
                        "join t.company c " +
                        "join t.memberList m",
                Team.class).getResultList();
    }

    @Override
    List<Team> findAll_fetchJoin() {
        return em.createQuery(
                "select distinct t from Team t " +
                        "join fetch t.company c " +
                        "join fetch t.memberList m",
                Team.class).getResultList();
    }

    @Override
    public List<Team> findAll_paging_inMemory() {
        return em.createQuery(
                "select distinct t from Team t " +
                        "join fetch t.company c ",
                Team.class).getResultList();
    }

    @Override
    List<Team> findAll_paging_inDB() {
        return null;
    }




}
