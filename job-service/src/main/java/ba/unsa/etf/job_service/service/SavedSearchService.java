package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.SavedSearchDTO;
import ba.unsa.etf.job_service.model.SavedSearch;
import ba.unsa.etf.job_service.model.User;
import ba.unsa.etf.job_service.repository.SavedSearchRepository;
import ba.unsa.etf.job_service.repository.UserRepository;  // To fetch the user by UUID
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedSearchService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SavedSearchRepository savedSearchRepository;

    @Autowired
    private UserRepository userRepository;  // Assuming you have a UserRepository to fetch users

    // Convert SavedSearch entity to SavedSearchDTO
    public SavedSearchDTO convertToDTO(SavedSearch savedSearch) {
        return modelMapper.map(savedSearch, SavedSearchDTO.class);
    }

    // Convert SavedSearchDTO to SavedSearch entity
    public SavedSearch convertToEntity(SavedSearchDTO savedSearchDTO) {
        return modelMapper.map(savedSearchDTO, SavedSearch.class);
    }

    // Save search and return the saved DTO
    public SavedSearchDTO saveSearch(SavedSearchDTO savedSearchDTO, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new SavedSearch entity
        SavedSearch savedSearch = new SavedSearch();
        savedSearch.setKeywords(savedSearchDTO.getKeywords());
        savedSearch.setLocation(savedSearchDTO.getLocation());
        savedSearch.setEmploymentType(savedSearchDTO.getEmploymentType());
        savedSearch.setSaveDate(savedSearchDTO.getSaveDate());
        savedSearch.setUser(user);

        // Save it to the database
        savedSearchRepository.save(savedSearch);

        // Return the SavedSearchDTO
        return savedSearchDTO;
    }

    public Page<SavedSearchDTO> getUserSavedSearches(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SavedSearch> savedSearches = savedSearchRepository.findByUser_Id(userId, pageable);

        return savedSearches.map(savedSearch -> modelMapper.map(savedSearch, SavedSearchDTO.class));
    }

}
