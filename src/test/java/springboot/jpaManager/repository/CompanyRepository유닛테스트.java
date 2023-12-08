package springboot.jpaManager.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Team;

import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CompanyRepository유닛테스트 {

    @Autowired
    TestEntityManager tem;
    @Autowired
    CompanyRepository repository;

    @Test
    void findCompanyWithTeamsById(){
        Optional<Company> company = repository.findCompanyWithTeamsById(1L);
        Company company1 = company.orElseThrow(() -> new NoSuchElementException());

        Company company2 = tem.getEntityManager().createQuery("select c from Company c where c.id=:id", Company.class)
                .setParameter("id", 1L)
                .getSingleResult();
        Assertions.assertThat(company1).isEqualTo(company2);
    }
}
