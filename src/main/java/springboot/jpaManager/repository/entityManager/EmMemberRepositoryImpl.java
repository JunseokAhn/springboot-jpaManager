package springboot.jpaManager.repository.entityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmMemberRepositoryImpl implements EmMemberRepository {

    private final EntityManager em;
    @Override
    public List<Member> findWithPaging(int start, int end) {
        return em.createQuery(
                        "select distinct m from Member m ",
                        Member.class)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
    }

}
