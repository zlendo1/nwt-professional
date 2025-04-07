package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.CompanyDTO;
import ba.unsa.etf.job_service.model.Company;
import ba.unsa.etf.job_service.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  @Autowired private ModelMapper modelMapper;
  @Autowired private CompanyRepository companyRepository;

  public CompanyDTO convertToDTO(Company company) {
    return modelMapper.map(company, CompanyDTO.class);
  }

  public Company convertToEntity(CompanyDTO companyDTO) {
    return modelMapper.map(companyDTO, Company.class);
  }
}
