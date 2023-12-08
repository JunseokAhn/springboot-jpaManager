package springboot.jpaManager.repository.entityManager;

import springboot.jpaManager.domain.Member;

import java.util.List;

public interface EmMemberRepository {
    public List<Member> findWithPaging(int start, int end);
}
