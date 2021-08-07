package springboot.jpaManager.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Company;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {

    private final EntityManager em;

    public Long save(Company company) {
        em.persist(company);
        return company.getId();
    }

    public Company findOne(Long companyId) {
        return em.find(Company.class, companyId);
    }

    public List<Company> findAll() {
        return em.createQuery("select c from Company c", Company.class).getResultList();
    }
    /*
    distinct > db에서 중복데이터를 제거할뿐만 아니라,
    자바로 데이터를 가져온 후 Entity를 기준으로 조인되는 Many측 데이터를
    List로 집어넣어준다.

    fetch join을 사용하여 팀리스트를 한방쿼리로 긁어온다.
    */
    public List<Company> findAll_v2() {
        return em.createQuery(
                "select distinct c from Company c " +
                "join fetch c.address a " +
                "join fetch c.teamList t ",
                Company.class).getResultList();
    }
    /*
    일대다인 주소까지만 페치조인을 하고,
    팀리스트는 회사 엔티티에서 한번 더 조회하는방식 > 페이징처리가 가능해진다
    */
    public List<Company> findAll_v3() {
        return em.createQuery(
                "select distinct c from Company c " +
                        "join fetch c.address a ",
                Company.class).getResultList();
    }

    public List<Company> findByName(String companyName) {
        return em.createQuery("select c from Company c where c.name = :name", Company.class)
                .setParameter("name", companyName).getResultList();
    }

    public void delete(Company company) {
        em.remove(company);
    }

    public void flush() {
        em.flush();
    }

    public void clear() {
        em.clear();
    }
}
