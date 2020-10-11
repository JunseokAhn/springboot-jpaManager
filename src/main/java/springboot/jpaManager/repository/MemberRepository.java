package springboot.jpaManager.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public void update(Member origin, Member member) {
        origin.updateMember(member);
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public Member findOne(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String memberName) {
        return em.createQuery("select m from Member m where m.name =:name", Member.class)
                .setParameter("name", memberName).getResultList();
    }


}
