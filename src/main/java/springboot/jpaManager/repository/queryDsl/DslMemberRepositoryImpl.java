package springboot.jpaManager.repository.queryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.repository.entityManager.EmMemberRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DslMemberRepositoryImpl implements DslMemberRepository {

    private final JPAQueryFactory queryFactory;

    public DslMemberRepositoryImpl(EntityManager entityManager){
        this.queryFactory= new JPAQueryFactory(entityManager);
    }
}
