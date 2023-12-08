package springboot.jpaManager.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.repository.entityManager.EmTeamRepository;
import springboot.jpaManager.repository.queryDsl.DslTeamRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, EmTeamRepository {
    @EntityGraph(attributePaths = "company")
    Optional<Team> findTeamWithCompanyById(Long teamId);
}
