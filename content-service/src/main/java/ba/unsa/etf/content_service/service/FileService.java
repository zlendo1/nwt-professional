package ba.unsa.etf.content_service.service;

import ba.unsa.etf.content_service.entity.File;
import ba.unsa.etf.content_service.repository.FileRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {

  private final FileRepository fileRepository;

  @Autowired
  public FileService(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Transactional(readOnly = true)
  public List<File> getAllFiles() {
    return fileRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<File> getFileById(Long id) {
    return fileRepository.findById(id);
  }

  @Transactional
  public File createFile(File file) {
    return fileRepository.save(file);
  }

  @Transactional
  public void deleteFile(Long id) {
    fileRepository.deleteById(id);
  }
}
