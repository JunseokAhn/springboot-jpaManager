package springboot.jpaManager.repository.entityManager;

import lombok.RequiredArgsConstructor;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.repository.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public abstract class EmRepository<T> implements Repository<T> {

    final EntityManager em;

    public void delete(T t) {
        em.remove(t);
    }

    public void flush() {
        em.flush();
    }

    public abstract List<T> findWithPaging(int start, int end);
}
