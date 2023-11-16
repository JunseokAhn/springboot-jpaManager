package springboot.jpaManager.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpaManager.domain.Company;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
public class JpqlCompanyRepository extends JpqlRepository<Company> {

    public JpqlCompanyRepository(EntityManager em) {
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
    List<Company> findAll_noOption() {
        return em.createQuery(
                "select c from Company c " +
                        "join c.teamList t ",
                Company.class).getResultList();
    }

    @Override
    List<Company> findAll_distinct() {
        return em.createQuery(
                "select distinct c from Company c " +
                        "join c.teamList t ",
                Company.class).getResultList();
    }


    @Override
    List<Company> findAll_fetchJoin() {
        return em.createQuery(
                "select distinct c from Company c " +
                        "join fetch c.teamList t ",
                Company.class).getResultList();
    }


    @Override
    List<Company> findAll_paging_inMemory() {
        return em.createQuery(
                "select distinct c from Company c",
                Company.class).getResultList();
    }

    @Override
    List<Company> findAll_paging_inDB() {
        return null;
    }


}
