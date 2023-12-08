package springboot.jpaManager.repository.entityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmTeamRepositoryImpl implements EmTeamRepository {

    private final EntityManager em;

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
