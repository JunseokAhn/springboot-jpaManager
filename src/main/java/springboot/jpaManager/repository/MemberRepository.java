package springboot.jpaManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.repository.entityManager.EmMemberRepository;
import springboot.jpaManager.repository.queryDsl.DslTeamRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, EmMemberRepository {
    List<Member> findByName(String name);

    List<Member> findByTeamId(long teamId);
}
