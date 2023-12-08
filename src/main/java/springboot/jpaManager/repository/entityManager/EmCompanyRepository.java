package springboot.jpaManager.repository.entityManager;

import springboot.jpaManager.domain.Company;

import java.util.List;

public interface EmCompanyRepository {
    List<Company> findWithPaging(int start, int end);
}
