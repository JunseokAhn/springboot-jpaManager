package springboot.jpaManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpaManager.domain.Address;
import springboot.jpaManager.domain.Company;
import springboot.jpaManager.repository.CompanyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public Long saveCompany(Long id, String name, Address address) {
        Company company = Company.createCompany(name, address);
        return companyRepository.save(company);
    }

    @Transactional
    public void updateCompany(Long id, String name, Address address) {
        Company origin = companyRepository.findOne(id);
        origin.update(name, address);
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        companyRepository.delete(company);
    }

    public Company findOne(Long companyId) {
        return companyRepository.findOne(companyId);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
