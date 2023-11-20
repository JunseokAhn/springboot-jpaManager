package springboot.jpaManager;

import springboot.jpaManager.domain.MemberStatus;
import springboot.jpaManager.dto.AddressDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;

public class TestUtils {

    public static CompanyDTO createCompanyDTO(String companyName) {

        CompanyDTO company = new CompanyDTO();
        company.setName(companyName);
        company.setAddress(createAddressDTO());

        return company;
    }

    public static CompanyDTO createCompanyDTO(Long companyId, String companyName) {

        CompanyDTO company = new CompanyDTO();
        company.setId(companyId);
        company.setName(companyName);
        company.setAddress(createAddressDTO());

        return company;
    }

    public static CompanyDTO createCompanyDTO() {

        CompanyDTO company = new CompanyDTO();
        company.setName("name");
        company.setAddress(createAddressDTO());

        return company;
    }

    private static AddressDTO createAddressDTO() {

        AddressDTO address = new AddressDTO();
        address.setCity("city");
        address.setStreet("street");
        address.setZipcode("zipcode");

        return address;
    }

    public static MemberDTO createMemberDTO(Long teamId) {

        MemberDTO member = new MemberDTO();
        member.setName("name");
        member.setSalary(1);
        member.setRank("rank");
        member.setAddress(createAddressDTO());
        member.setStatus(MemberStatus.WAIT);
        member.setTeamId(teamId);

        return member;
    }
    public static MemberDTO createMemberDTO(Long teamId, String memberName) {

        MemberDTO member = new MemberDTO();
        member.setName(memberName);
        member.setSalary(1);
        member.setRank("rank");
        member.setAddress(createAddressDTO());
        member.setStatus(MemberStatus.WAIT);
        member.setTeamId(teamId);

        return member;
    }
    public static MemberDTO createMemberDTO(String memberName) {

        MemberDTO member = new MemberDTO();
        member.setName(memberName);
        member.setSalary(1);
        member.setRank("rank");
        member.setAddress(createAddressDTO());
        member.setStatus(MemberStatus.WAIT);

        return member;
    }

    public static TeamDTO createTeamDTO(long companyId) {

        TeamDTO team = new TeamDTO();
        team.setName("name");
        team.setTask("task");
        team.setMemberCount(0);
        team.setCompanyId(companyId);

        return team;
    }


    public static TeamDTO createTeamDTO(long companyId, String teamName) {

        TeamDTO team = new TeamDTO();
        team.setName(teamName);
        team.setTask("task");
        team.setMemberCount(0);
        team.setCompanyId(companyId);

        return team;
    }
    public static TeamDTO createTeamDTO(String teamName) {

        TeamDTO team = new TeamDTO();
        team.setName(teamName);
        team.setTask("task");
        team.setMemberCount(0);

        return team;
    }
}
