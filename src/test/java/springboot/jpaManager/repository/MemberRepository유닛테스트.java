package springboot.jpaManager.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import springboot.jpaManager.domain.Member;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MemberRepository유닛테스트 {

    @Autowired
    private MemberRepository repository;
    @Autowired
    TestEntityManager tem;
    @Test
    void findByTeamId(){
        List<Member> members1 = repository.findByTeamId(1L);
        List<Member> members2 = tem.getEntityManager().createQuery("select m from Member m where m.team.id =: teamId", Member.class)
                .setParameter("teamId", 1L)
                .getResultList();
        for (int i = 0; i < members1.size(); i++) {
            Assertions.assertThat(members1.get(i)).isEqualTo(members2.get(i));
        }
    }

    @Test
    void findByName(){
        List<Member> members1 = repository.findByName("멤버1");
        List<Member> members2 = tem.getEntityManager().createQuery("select m from Member m where m.name =: name", Member.class)
                .setParameter("name", "멤버1")
                .getResultList();
        for (int i = 0; i < members1.size(); i++) {
            Assertions.assertThat(members1.get(i)).isEqualTo(members2.get(i));
        }
    }
}
