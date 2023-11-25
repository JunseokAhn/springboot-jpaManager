package springboot.jpaManager.repository.entityManager;

import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmCompanyRepository extends EmRepository<Company> {

    public EmCompanyRepository(EntityManager em) {
        super(em);
    }

    public Long save(Company company) {
        em.persist(company);
        return company.getId();
    }

    public Company findOne(Long companyId) {
        return em.find(Company.class, companyId);
    }


    public List<Company> findByName(String companyName) {
        return em.createQuery("select c from Company c where c.name = :name", Company.class)
                .setParameter("name", companyName).getResultList();
    }

    @Override
    public List<Company> findAll() {
        return em.createQuery(
                "select distinct c from Company c " +
                        "join fetch c.teamList t ",
                Company.class).getResultList();
    }


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
