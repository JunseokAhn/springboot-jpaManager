package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.repository.CompanyRepository;
import springboot.jpaManager.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;
    private final Utils utils;

    @Transactional
    public Long saveCompany(CompanyDTO companyDTO) {
        Company company = utils.map(companyDTO, Company.class);
        return utils.isNotReflected(companyRepository.save(company).getId());
    }

    @Transactional
    public void updateCompany(CompanyDTO.UpdateAll companyDTO) {
        Company company = this.findOne(companyDTO.getId());
        company.updateAll(companyDTO);
    }

    @Transactional
    public void deleteCompany(Long companyId) {

        Company company = this.findOne(companyId);
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
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException(("company not found")));
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
    public Company findByIdWithTeam(Long companyId){
        return companyRepository.findCompanyWithTeamsById(companyId)
                .orElseThrow(() -> new NoSuchElementException("company not found"));
    }
}


