package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Address;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.repository.CompanyRepository;
import springboot.jpaManager.repository.MemberRepository;
import springboot.jpaManager.repository.TeamRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveCompany(Company company) {

        return companyRepository.save(company);
    }

    @Transactional
    public void updateCompany(Long companyId, Company company) {
        Company origin = companyRepository.findOne(companyId);
        origin.update(company);
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        companyRepository.delete(company);
        List<Team> teamList = company.getTeamList();
        for (Team team : teamList) {
            List<Member> memberList = team.getMemberList();
            for (Member member : memberList) {
                memberRepository.delete(member);
            }
        }
    }

    public Company findOne(Long companyId) {
        return companyRepository.findOne(companyId);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public void flush() {
        companyRepository.flush();
    }
}
