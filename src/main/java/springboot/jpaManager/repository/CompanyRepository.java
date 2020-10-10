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

    public List<Company> findByName(String companyName) {
        return em.createQuery("select c from Company c where c.name = :name", Company.class)
                .setParameter("name", companyName).getResultList();
    }

}
