package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Address;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.CompanyDTO;
import springboot.jpaManager.repository.CompanyRepository;
import springboot.jpaManager.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;

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
    public void updateCompany(CompanyDTO companyDTO) {
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
        String name = companyDTO.getName();

        String city = companyDTO.getCity();
        String street = companyDTO.getStreet();
        String zipcode = companyDTO.getZipcode();
        Address address = Address.createAddress(city, street, zipcode);

        return Company.createCompany(name, address);
    }

    public static CompanyDTO transDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setCity(company.getAddress().getCity());
        companyDTO.setStreet(company.getAddress().getStreet());
        companyDTO.setZipcode(company.getAddress().getZipcode());
        return companyDTO;
    }

    public static List<CompanyDTO> transDTOList(List<Company> companyList) {

        List<CompanyDTO> companyDTOList = companyList.stream().map(m -> new CompanyDTO
                (m.getId(), m.getName(), m.getAddress().getCity(), m.getAddress().getStreet(), m.getAddress().getZipcode()))
                .collect(Collectors.toList());

        return companyDTOList;
    }
}
