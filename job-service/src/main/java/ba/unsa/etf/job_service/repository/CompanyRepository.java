package ba.unsa.etf.job_service.repository;

import ba.unsa.etf.job_service.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyUUID(String companyUUID);

    Optional<Company> findByName(String companyName);
}
