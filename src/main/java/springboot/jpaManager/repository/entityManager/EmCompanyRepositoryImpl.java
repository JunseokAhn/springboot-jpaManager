package springboot.jpaManager.repository.entityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Company;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmCompanyRepositoryImpl implements EmCompanyRepository {

    private final EntityManager em;

    @Override
    public List<Company> findWithPaging(int start, int end) {
        return em.createQuery(
                        "select distinct c from Company c ",
                        Company.class)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
    }

}
