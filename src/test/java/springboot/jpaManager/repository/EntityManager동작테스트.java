package springboot.jpaManager.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.repository.entityManager.EmTeamRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class EntityManager동작테스트 {

    /*********************************************
     EntityManager 동착테스트
     ---------------------------------------------
     Jpql과 프록시상태의 인스턴스, 영속성의 관계애대해 알아보자.

     1 Company : 3 Team : 9 Memeber
     각 팀과 멤버는 3개씩 상위 객체에 속함.

     필요에 따라 Company = 1 / Team = N / Member = M 으로 지칭
     **********************************************/

    @Autowired
    private TestEntityManager tem;
    private EntityManager em;

    /**
     * distinct가 없으므로 데이터레코드만큼 인스턴스 생성
     */
    @Test
    public void findAll_noDistinct() throws Exception {
        em = tem.getEntityManager();
        List<Team> teams = em.createQuery(
                "select t from Team t " +
                        "left outer join t.company c " +
                        "left outer join t.memberList m",
                Team.class).getResultList();
        /*
            select
                team0_.team_id as team_id1_2_,
                team0_.company_id as company_4_2_,
                team0_.name as name2_2_,
                team0_.task as task3_2_
            from
                team team0_
            left outer join
                company company1_
                    on team0_.company_id=company1_.company_id
            left outer join
                member memberlist2_
                    on team0_.team_id=memberlist2_.team_id

        */

        //distinct가 없으므로 데이터레코드가 9개발생, 완전히 같은 team 인스턴스 N이 M 개 발생.
        assertThat(teams.size()).isEqualTo(3 * 3);
        assertThat(teams.get(0)).isEqualTo(teams.get(1)).isEqualTo(teams.get(2));
        assertThat(teams.get(3)).isEqualTo(teams.get(4)).isEqualTo(teams.get(5));
        assertThat(teams.get(6)).isEqualTo(teams.get(7)).isEqualTo(teams.get(8));

        // select * from member N번 발생, batchSize설정으로 where in으로 변환
        teams.forEach(team -> team.getMemberList().forEach(member -> member.getName()));
        em.close();
    }

    /**
     * distinct > db에서 중복데이터를 제거 <br>
     */
    @Test
    public void findAll_distinct() throws Exception {
        em = tem.getEntityManager();
        List<Team> teams = em.createQuery(
                "select distinct t from Team t " +
                        "left outer join t.company c " +
                        "left outer join t.memberList m",
                Team.class).getResultList();
        /*
            select
                distinct team0_.team_id as team_id1_2_,
                team0_.company_id as company_4_2_,
                team0_.name as name2_2_,
                team0_.task as task3_2_
            from
                team team0_
            left outer join
                company company1_
                    on team0_.company_id=company1_.company_id
            left outer join
                member memberlist2_
                    on team0_.team_id=memberlist2_.team_id
        */

        //distinct로 데이터레코드 3개 발생, team인스턴스 3개 생성
        assertThat(teams.size()).isEqualTo(3);

        // select * from member N번 발생, batchSize설정으로 where in으로 변환
        teams.forEach(team -> team.getMemberList().forEach(member -> member.getName()));
        em.close();
    }

    /**
     * 1. distinct가 없으므로 데이터레코드만큼 인스턴스 생성 <br>
     * 2. fetch조인이므로 Entity를 기준으로 인메모리에서 중복을 제거하고 List반환 <br>
     */
    @Test
    public void findAll_fetchJoin_noDistinct() {
        em = tem.getEntityManager();
        List<Team> teams = em.createQuery(
                "select t from Team t " +
                        "left outer join fetch t.company c " +
                        "left outer join fetch t.memberList m",
                Team.class).getResultList();
        /*
            select
                team0_.team_id as team_id1_2_0_,
                company1_.company_id as company_1_0_1_,
                memberlist2_.member_id as member_i2_1_2_,
                team0_.company_id as company_4_2_0_,
                team0_.name as name2_2_0_,
                team0_.task as task3_2_0_,
                company1_.city as city2_0_1_,
                company1_.street as street3_0_1_,
                company1_.zipcode as zipcode4_0_1_,
                company1_.name as name5_0_1_,
                memberlist2_.city as city3_1_2_,
                memberlist2_.street as street4_1_2_,
                memberlist2_.zipcode as zipcode5_1_2_,
                memberlist2_.name as name6_1_2_,
                memberlist2_.rank as rank7_1_2_,
                memberlist2_.salary as salary8_1_2_,
                memberlist2_.status as status9_1_2_,
                memberlist2_.team_id as team_id11_1_2_,
                memberlist2_.type as type10_1_2_,
                memberlist2_.dtype as dtype1_1_2_,
                memberlist2_.team_id as team_id11_1_0__,
                memberlist2_.member_id as member_i2_1_0__
            from
                team team0_
            left outer join
                company company1_
                    on team0_.company_id=company1_.company_id
            left outer join
                member memberlist2_
                    on team0_.team_id=memberlist2_.team_id
         */


        // distinct가 없으므로 데이터레코드가 9개 발생
        assertThat(teams.size()).isEqualTo(3 * 3);
        assertThat(teams.get(0)).isEqualTo(teams.get(1)).isEqualTo(teams.get(2));
        assertThat(teams.get(3)).isEqualTo(teams.get(4)).isEqualTo(teams.get(5));
        assertThat(teams.get(6)).isEqualTo(teams.get(7)).isEqualTo(teams.get(8));

        // fetch조인이므로 하이버네이트가 이미 메모리에서 Member의 중복을 제거하고 List를 반환하였음
        // 쿼리발생 x
        teams.forEach(team -> team.getMemberList().forEach(member -> member.getName()));
        em.close();
    }

    /**
     * 1. distinct > db에서 중복데이터를 제거 <br>
     * 2. fetch조인이므로 Entity를 기준으로 인메모리에서 중복을 제거하고 List반환 <br>
     * 페이징이 없다면 이 방식이 권장옵션
     */
    @Test
    public void findAll_fetchJoin() throws Exception {
        em = tem.getEntityManager();
        List<Team> teams = em.createQuery(
                        "select distinct t from Team t " +
                                "left outer join fetch t.company c " +
                                "left outer join fetch t.memberList m",
                        Team.class)
                .getResultList();
        /*
             select
                 distinct team0_.team_id as team_id1_2_0_,
                 company1_.company_id as company_1_0_1_,
                 memberlist2_.member_id as member_i2_1_2_,
                 team0_.company_id as company_4_2_0_,
                 team0_.name as name2_2_0_,
                 team0_.task as task3_2_0_,
                 company1_.city as city2_0_1_,
                 company1_.street as street3_0_1_,
                 company1_.zipcode as zipcode4_0_1_,
                 company1_.name as name5_0_1_,
                 memberlist2_.city as city3_1_2_,
                 memberlist2_.street as street4_1_2_,
                 memberlist2_.zipcode as zipcode5_1_2_,
                 memberlist2_.name as name6_1_2_,
                 memberlist2_.rank as rank7_1_2_,
                 memberlist2_.salary as salary8_1_2_,
                 memberlist2_.status as status9_1_2_,
                 memberlist2_.team_id as team_id11_1_2_,
                 memberlist2_.type as type10_1_2_,
                 memberlist2_.dtype as dtype1_1_2_,
                 memberlist2_.team_id as team_id11_1_0__,
                 memberlist2_.member_id as member_i2_1_0__
             from
                team team0_
            left outer join
                company company1_
                    on team0_.company_id=company1_.company_id
            left outer join
                member memberlist2_
                    on team0_.team_id=memberlist2_.team_id
         */


        // distinct로 데이터레코드 3개 발생, team인스턴스 3개 생성
        assertThat(teams.size()).isEqualTo(3);

        // fetch조인이므로 하이버네이트가 이미 메모리에서 Member의 중복을 제거하고 List를 반환하였음
        // 쿼리발생 x
        teams.forEach(team -> team.getMemberList().forEach(member -> member.getName()));
        em.close();
    }

    /**
     * 페이징이 필요한 엔티티를 포함하여 조회 <br>
     * fetchJoin으로 모든 레코드를 가져와 어플리케이션 인메모리에서 페이징처리 <br>
     * toOne관계면 상관없지만, toMany관계면 어플리케이션에 부하가 발생하므로 비권장옵션
     */
    @Test
    public void findAll_paging_inMemory() throws Exception {
        em = tem.getEntityManager();
        List<Team> teams = em.createQuery(
                        "select distinct t from Team t " +
                                "left outer join fetch t.company c " +
                                "left outer join fetch t.memberList m",
                        Team.class)
                .setFirstResult(1)
                .setMaxResults(2)
                .getResultList();
        /*
            select
                distinct team0_.team_id as team_id1_2_0_,
                company1_.company_id as company_1_0_1_,
                memberlist2_.member_id as member_i2_1_2_,
                team0_.company_id as company_4_2_0_,
                team0_.name as name2_2_0_,
                team0_.task as task3_2_0_,
                company1_.city as city2_0_1_,
                company1_.street as street3_0_1_,
                company1_.zipcode as zipcode4_0_1_,
                company1_.name as name5_0_1_,
                memberlist2_.city as city3_1_2_,
                memberlist2_.street as street4_1_2_,
                memberlist2_.zipcode as zipcode5_1_2_,
                memberlist2_.name as name6_1_2_,
                memberlist2_.rank as rank7_1_2_,
                memberlist2_.salary as salary8_1_2_,
                memberlist2_.status as status9_1_2_,
                memberlist2_.team_id as team_id11_1_2_,
                memberlist2_.type as type10_1_2_,
                memberlist2_.dtype as dtype1_1_2_,
                memberlist2_.team_id as team_id11_1_0__,
                memberlist2_.member_id as member_i2_1_0__
            from
                team team0_
            left outer join
                company company1_
                    on team0_.company_id=company1_.company_id
            left outer join
                member memberlist2_
                    on team0_.team_id=memberlist2_.team_id
        */
        // distinct로 데이터레코드 3개 발생, 쿼리결과를 인메모리에서 조립하여 team인스턴스 2개 생성
        assertThat(teams.size()).isEqualTo(2);

        // fetch조인이므로 하이버네이트가 이미 메모리에서 Member의 중복을 제거하고 List를 반환하였음
        // 쿼리발생 x
        teams.forEach(team -> team.getMemberList().forEach(member -> member.getName()));
        em.close();
    }

    /**
     * 1. toOne관계의 엔티티는 DB레벨에서 페이징처리 가능 <br>
     * 2. toMany관계의 엔티티는 제외하고 조회하고, 이후 Many엔티티가 필요하면 레코드단위로 새로 조회가 발생한다. <br>
     * 3. BatchSize 조정으로 where in 조건으로 치환가능. <br>
     * 이 부분은 옵티마이저가 최대한 최적해줄것이므로 페이징관련 최적의 옵션
     */
    @Test
    public void findAll_paging_inDB() throws Exception {
        em = tem.getEntityManager();
        List<Team> teams = em.createQuery(
                        "select distinct t from Team t " +
                                "left outer join fetch t.company c",
                        Team.class)
                .setFirstResult(1)
                .setMaxResults(2)
                .getResultList();
        /*
             select
                distinct team0_.team_id as team_id1_2_0_,
                 company1_.company_id as company_1_0_1_,
                 team0_.company_id as company_4_2_0_,
                 team0_.name as name2_2_0_,
                 team0_.task as task3_2_0_,
                 company1_.city as city2_0_1_,
                 company1_.street as street3_0_1_,
                 company1_.zipcode as zipcode4_0_1_,
                 company1_.name as name5_0_1_
             from
                team team0_
             inner join
                company company1_
                    on team0_.company_id=company1_.company_id limit ? offset ?
         */


        // distinct로 데이터레코드 3개 발생, 쿼리결과를 인메모리에서 조립하여 team인스턴스 2개 생성
        assertThat(teams.size()).isEqualTo(2);

        // member를 의도적으로 제거하였으므로 필요시에 새로 조회
        // select * from member N번 발생, batchSize설정으로 where in으로 변환
        teams.forEach(team -> team.getMemberList().forEach(member -> member.getName()));
        em.close();
    }

}