package springboot.jpaManager.common;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static springboot.jpaManager.TestUtils.*;

public class UtilsUnitTest {

    ModelMapper mapper= new ModelMapper();
    Utils utils= new Utils(mapper);

    @Before
    public void init(){
        // private생성자 매핑 허용
        mapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        // Member entity to DTO 매핑설정
        mapper.typeMap(Member.class, MemberDTO.List.class).addMappings(mapper -> {
            mapper.map(Member -> Member.getTeam().getName(), MemberDTO.List::setTeamName);
            mapper.map(Member -> Member.getTeam().getCompany().getName(), MemberDTO.List::setCompanyName);
        });

        // Team entity to DTO 매핑설정
        mapper.typeMap(Team.class, TeamDTO.Update.class).addMappings(mapper -> {
            mapper.map(Team -> Team.getCompany().getId(), TeamDTO.Update::setCompanyId);
            mapper.map(Team -> Team.getCompany().getName(), TeamDTO.Update::setCompanyName);
        });
    }
    @Test
    public void map() {
        //given
        CompanyDTO companyDTO1 = createCompanyDTO("COMPANY1");
        TeamDTO teamDTO1 = createTeamDTO("TEAM1");
        MemberDTO memberDTO1 = createMemberDTO("MEMBER1");
        MemberDTO memberDTO2 = createMemberDTO("MEMBER2");
        MemberDTO memberDTO3 = createMemberDTO("MEMBER3");

        //when
        Company company1 = utils.map(companyDTO1, Company.class);
        Team team1 = utils.map(teamDTO1, Team.class);
        Member member1 = utils.map(memberDTO1, Member.class);

        List<MemberDTO> memberDTOs = List.of(memberDTO1, memberDTO2, memberDTO3);
        List<Member> members = utils.map(memberDTOs, Member.class);

        //then
        Assertions.assertThat(company1.getName()).isEqualTo(companyDTO1.getName());
        Assertions.assertThat(team1.getName()).isEqualTo(teamDTO1.getName());
        Assertions.assertThat(member1.getName()).isEqualTo(memberDTO1.getName());

        for (int i=0; i< members.size(); i++) {
            Assertions.assertThat(members.get(i).getName()).isEqualTo(memberDTOs.get(i).getName());
        }

    }

    @Test
    public void isNotReflected() {

        // success test
        CompanyDTO companyDTO1 = createCompanyDTO(999L, "COMPANY1");
        assertThatCode(() -> utils.isNotReflected(companyDTO1.getId())).doesNotThrowAnyException();
        
        // fail test
        CompanyDTO companyDTO2 = createCompanyDTO(-999L, "COMPANY2");
        Assertions.assertThatThrownBy(() -> utils.isNotReflected(companyDTO2.getId())).isInstanceOf(RuntimeException.class);
    }
}