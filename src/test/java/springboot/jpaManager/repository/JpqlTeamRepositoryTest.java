package springboot.jpaManager.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;

import java.util.List;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class JpqlTeamRepositoryTest {

    @Autowired
    JpqlTeamRepository repository;

    @Test
    public void findAll_noDistinct() throws Exception {
        List<Team> teams = repository.findAll_noDistinct();
        /*
            select
                team0_.team_id as team_id1_2_,
                team0_.company_id as company_4_2_,
                team0_.name as name2_2_,
                team0_.task as task3_2_
            from
                team team0_
            inner join
                company company1_
                    on team0_.company_id=company1_.company_id
            inner join
                member memberlist2_
                    on team0_.team_id=memberlist2_.team_id

        */
        for (Team team : teams) {
            //멤버가 각 팀마다 3명씩 속하기때문에, 같은 team객체가 3번 반환됨.
            // 쿼리발생 x, size M
            System.out.println(team);
            //springboot.jpaManager.domain.Team@264b1547
            //springboot.jpaManager.domain.Team@264b1547
            //springboot.jpaManager.domain.Team@264b1547
            //springboot.jpaManager.domain.Team@5c74b3f5
            //springboot.jpaManager.domain.Team@5c74b3f5
            //springboot.jpaManager.domain.Team@5c74b3f5
            //springboot.jpaManager.domain.Team@42a07701
            //springboot.jpaManager.domain.Team@42a07701
            //springboot.jpaManager.domain.Team@42a07701
        }

        for (Team team : teams) {
            for (Member member : team.getMemberList()) {
                // 아래에 해당하는 쿼리가 N번 발생
                // select * from member where teamid = ?
                System.out.println(member.getName());
            }
        }

    }

    @Test
    public void findAll_distinct() throws Exception {
        List<Team> teams = repository.findAll_distinct();
        /*
            select
                distinct team0_.team_id as team_id1_2_,
                team0_.company_id as company_4_2_,
                team0_.name as name2_2_,
                team0_.task as task3_2_
            from
                team team0_
            inner join
                company company1_
                    on team0_.company_id=company1_.company_id
            inner join
                member memberlist2_
                    on team0_.team_id=memberlist2_.team_id
        */

        for (Team team : teams) {
            // 쿼리발생 x, size N
            System.out.println(team);

        }

        for (Team team : teams) {
            for (Member member : team.getMemberList()) {
                // 아래에 해당하는 쿼리가 N번 발생
                // select * from member where teamid = ?
                System.out.println(member.getName());
            }

        }
    }

    @Test
    public void findAll_fetchJoin_noDistinct(){
        List<Team> teams = repository.findAll_fetchJoin_noDistinct();
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
        inner join
            company company1_
                on team0_.company_id=company1_.company_id
        inner join
            member memberlist2_
                on team0_.team_id=memberlist2_.team_id
         */

        for (Team team : teams) {
            //쿼리발생 x, size : M
            System.out.println(team);
        }

        for (Team team : teams) {
            for (Member member : team.getMemberList()) {
                //쿼리발생 x, size N*M
                System.out.println(member.getName());
            }
        }
    }
    @Test
    public void findAll_fetchJoin() throws Exception {
        List<Team> teams = repository.findAll_fetchJoin();
        /** 직접 아래쿼리를 수행해보면 9개의 결과가 나오지만,
         * fetch join으로인해 3개의 결과값을 반환하고, toMany엔티티들이 list에 들어가있는모습.
         * 어플리케이션 인메모리에서 해당작업이 이루어짐.
         * 하지만 ORM에서 이정도의 인메모리작업은 사용할수밖에 없으므로, 페이징이 없다면 이 방식이 권장옵션
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
         inner join
            company company1_
                on team0_.company_id=company1_.company_id
         inner join
            member memberlist2_
                on team0_.team_id=memberlist2_.team_id
         */
        for (Team team : teams) {
            //쿼리발생 x, size N
            System.out.println(team);

        }

        for (Team team : teams) {
            for (Member member : team.getMemberList()) {
                //쿼리발생 x, size M
                System.out.println(member.getName());
            }
        }

    }

    @Test
    public void findAll_paging_inMemory() throws Exception {
        List<Team> teams = repository.findAll_paging_inMemory();
        /* 어차피 쿼리결과를 인메모리에서 조합하므로 시간복잡도는 위의 페치조인테스트와 마찬가지
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
        inner join
            company company1_
                on team0_.company_id=company1_.company_id
        inner join
            member memberlist2_
                on team0_.team_id=memberlist2_.team_id
        */
        for (Team team : teams) {
            //쿼리발생 x, size N
            System.out.println(team);
            //springboot.jpaManager.domain.Team@aa524b8
            //springboot.jpaManager.domain.Team@38984fcb
        }

        for (Team team : teams) {
            for (Member member : team.getMemberList()) {
                //쿼리발생 x, size M
                System.out.println(member.getName());
                //유저2-1
                //유저2-2
                //유저2-3
                //유저3-1
                //유저3-2
                //유저3-3
            }
        }
    }


    @Test
    public void findAll_paging_inDB() throws Exception {
        List<Team> teams = repository.findAll_paging_inDB();
        /* 1. toMany엔티티를 제외시키고 조회
         * 2. 이후 toMany엔티티 필요 시 where절에 in문법을 사용하여 batch사이즈만큼 조회
         * in 자체는 옵티마이저가 최적화해줄수 있는 부분이고, db커넥션이 최소화되므로
         * 페이징관련 최적화된 방법이라 할수있음.
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
        for (Team team : teams) {
            // 쿼리발생 x
            System.out.println(team);
            //springboot.jpaManager.domain.Team@aedd0e6
            //springboot.jpaManager.domain.Team@58c868c5
        }

        for (Team team : teams) {
            for (Member member : team.getMemberList()) {
                /* in조건을 사용하려면 엔티티의 필드나 프로젝트설정파일에 BatchSize를 지정해야한다.
                select
                    memberlist0_.team_id as team_id11_1_1_,
                    memberlist0_.member_id as member_i2_1_1_,
                    memberlist0_.member_id as member_i2_1_0_,
                    memberlist0_.city as city3_1_0_,
                    memberlist0_.street as street4_1_0_,
                    memberlist0_.zipcode as zipcode5_1_0_,
                    memberlist0_.name as name6_1_0_,
                    memberlist0_.rank as rank7_1_0_,
                    memberlist0_.salary as salary8_1_0_,
                    memberlist0_.status as status9_1_0_,
                    memberlist0_.team_id as team_id11_1_0_,
                    memberlist0_.type as type10_1_0_,
                    memberlist0_.dtype as dtype1_1_0_
                from
                    member memberlist0_
                where
                    memberlist0_.team_id in (
                        ?, ?
                    )
                */
                System.out.println(member.getName());
            }
        }
    }

}