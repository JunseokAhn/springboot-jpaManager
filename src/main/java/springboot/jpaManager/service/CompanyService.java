package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.api.CompanyApiController;
import springboot.jpaManager.domain.Address;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.AddressDTO;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.repository.CompanyRepository;
import springboot.jpaManager.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static springboot.jpaManager.api.CompanyApiController.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveCompany(CompanyDTO companyDTO) {
        Company company = transEntity(companyDTO);
        return companyRepository.save(company);
    }

    @Transactional
    public void updateCompany(CompanyDTO.UpdateAll companyDTO) {
        Company origin = companyRepository.findOne(companyDTO.getId());
        origin.update(companyDTO);
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

    private Company transEntity(CompanyDTO companyDTO) {

        return Company.builder()
                .name(companyDTO.getName())
                .address(companyDTO.getAddress().transEntity())
                .build();
    }

    public static CompanyDTO transDTO(Company company) {

        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress().transDTO())
                .build();
    }

    public static CompanyDTO.UpdateAll transDTO(Long id, UpdateCompanyRequest updateCompanyRequest) {

        return CompanyDTO.UpdateAll.builder()
                .id(id)
                .name(updateCompanyRequest.getName())
                .address(updateCompanyRequest.getAddress())
                .build();
    }

    public static List<CompanyDTO> transDTOList(List<Company> companyList) {

        List<CompanyDTO> companyDTOList = companyList.stream()
                .map(CompanyDTO::new)
                .collect(Collectors.toList());

        return companyDTOList;
    }
}
