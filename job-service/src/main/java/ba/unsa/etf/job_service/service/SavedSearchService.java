package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.SavedSearchDTO;
import ba.unsa.etf.job_service.model.SavedSearch;
import ba.unsa.etf.job_service.model.User;
import ba.unsa.etf.job_service.repository.SavedSearchRepository;
import ba.unsa.etf.job_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SavedSearchService {

  @Autowired private ModelMapper modelMapper;

  @Autowired private SavedSearchRepository savedSearchRepository;

  @Autowired
  private UserRepository userRepository; // Assuming you have a UserRepository to fetch users

  public SavedSearchDTO convertToDTO(SavedSearch savedSearch) {
    return modelMapper.map(savedSearch, SavedSearchDTO.class);
  }

  public SavedSearch convertToEntity(SavedSearchDTO savedSearchDTO) {
    return modelMapper.map(savedSearchDTO, SavedSearch.class);
  }

  public SavedSearchDTO saveSearch(SavedSearchDTO savedSearchDTO, Long id) {
    User user =
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    SavedSearch savedSearch = new SavedSearch();
    savedSearch.setKeywords(savedSearchDTO.getKeywords());
    savedSearch.setLocation(savedSearchDTO.getLocation());
    savedSearch.setEmploymentType(savedSearchDTO.getEmploymentType());
    savedSearch.setSaveDate(savedSearchDTO.getSaveDate());
    savedSearch.setUser(user);

    savedSearchRepository.save(savedSearch);

    return savedSearchDTO;
  }

  public Page<SavedSearchDTO> getUserSavedSearches(Long userId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<SavedSearch> savedSearches = savedSearchRepository.findByUser_Id(userId, pageable);

    return savedSearches.map(savedSearch -> modelMapper.map(savedSearch, SavedSearchDTO.class));
  }
}
