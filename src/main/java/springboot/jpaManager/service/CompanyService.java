package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.common.Utils;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.repository.entityManager.EmCompanyRepository;
import springboot.jpaManager.repository.entityManager.EmMemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final EmCompanyRepository companyRepository;
    private final EmMemberRepository memberRepository;
    private final Utils utils;

    @Transactional
    public Long saveCompany(CompanyDTO companyDTO) {
        Company company = utils.map(companyDTO, Company.class);
        return utils.isNotReflected(companyRepository.save(company));
    }

    @Transactional
    public void updateCompany(CompanyDTO.UpdateAll companyDTO) {
        Company origin = companyRepository.findOne(companyDTO.getId());
        origin.updateAll(companyDTO);
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

    public void flush() {
        companyRepository.flush();
    }

    public List<Company> findAll() {
        return companyRepository.findAll_fetchJoin();
    }
}


