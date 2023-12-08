package springboot.jpaManager.repository.entityManager;

import springboot.jpaManager.domain.Team;

import java.util.List;

public interface EmTeamRepository {
    public List<Team> findWithPaging(int start, int end);
}
