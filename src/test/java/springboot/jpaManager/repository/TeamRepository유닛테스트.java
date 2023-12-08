package springboot.jpaManager.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import springboot.jpaManager.domain.Team;

import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TeamRepository유닛테스트 {

    @Autowired
    TestEntityManager tem;
    @Autowired
    TeamRepository repository;

    @Test
    void teamWithCompanyById(){
        Optional<Team> teamWithCompanyById = repository.findTeamWithCompanyById(1L);
        Team team1 = teamWithCompanyById.orElseThrow(() -> new NoSuchElementException());

        Team team2 = tem.getEntityManager().createQuery("select t from Team t where t.id=:id", Team.class)
                .setParameter("id", 1L)
                .getSingleResult();
        Assertions.assertThat(team1).isEqualTo(team2);
    }
}
