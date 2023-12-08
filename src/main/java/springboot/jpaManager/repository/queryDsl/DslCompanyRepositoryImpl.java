package springboot.jpaManager.repository.queryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.repository.entityManager.EmCompanyRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DslCompanyRepositoryImpl implements DslCompanyRepository {

    private final JPAQueryFactory queryFactory;

    public DslCompanyRepositoryImpl(EntityManager entityManager){
        this.queryFactory= new JPAQueryFactory(entityManager);
    }
}
