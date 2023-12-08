package springboot.jpaManager.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.repository.entityManager.EmCompanyRepository;
import springboot.jpaManager.repository.queryDsl.DslTeamRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, EmCompanyRepository {
    @EntityGraph(attributePaths = "teamList")
    Optional<Company> findCompanyWithTeamsById(Long companyId);
}
