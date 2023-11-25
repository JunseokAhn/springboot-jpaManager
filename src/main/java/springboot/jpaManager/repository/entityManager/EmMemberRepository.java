package springboot.jpaManager.repository.entityManager;

import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmMemberRepository extends EmRepository<Member> {
    public EmMemberRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }


    public List<Member> findByName(String memberName) {
        return em.createQuery("select m from Member m where m.name =:name", Member.class)
                .setParameter("name", memberName).getResultList();
    }

    @Override
    public Member findOne(Long memberId) {
        return em.find(Member.class, memberId);
    }


    @Override
    public List<Member> findAll() {
        return em.createQuery(
                "select distinct m from Member m" +
                        " join fetch m.team t",
                Member.class).getResultList();
    }
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
