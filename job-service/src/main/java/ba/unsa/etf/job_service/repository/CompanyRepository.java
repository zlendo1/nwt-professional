package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
  Optional<Company> findByCompanyUUID(String companyUUID);

  Optional<Company> findByName(String companyName);
}
