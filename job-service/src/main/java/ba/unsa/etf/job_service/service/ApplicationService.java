package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.ApplicationDTO;
import ba.unsa.etf.job_service.model.Application;
import ba.unsa.etf.job_service.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationRepository applicationRepository;

    public ApplicationDTO convertToDTO(Application application) {
        return modelMapper.map(application, ApplicationDTO.class);
    }

    public Application convertToEntity(ApplicationDTO applicationDTO) {
        return modelMapper.map(applicationDTO, Application.class);
    }
}
