package springboot.jpaManager.repository.queryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.repository.entityManager.EmTeamRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DslTeamRepositoryImpl implements DslTeamRepository {

    private final JPAQueryFactory queryFactory;

    public DslTeamRepositoryImpl(EntityManager entityManager){
        this.queryFactory= new JPAQueryFactory(entityManager);
    }

}
