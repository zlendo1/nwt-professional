package ba.unsa.etf.job_service.controller;

import ba.unsa.etf.job_service.dto.SavedSearchDTO;
import ba.unsa.etf.job_service.service.SavedSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved-searches")
public class SavedSearchController {

  @Autowired private SavedSearchService savedSearchService;

  @PostMapping("/{userId}")
  public ResponseEntity<SavedSearchDTO> saveSearch(
      @PathVariable Long userId, @RequestBody SavedSearchDTO savedSearchDTO) {
    return ResponseEntity.ok(savedSearchService.saveSearch(savedSearchDTO, userId));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<Page<SavedSearchDTO>> getUserSavedSearches(
      @PathVariable Long userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(savedSearchService.getUserSavedSearches(userId, page, size));
  }
}
