package ba.unsa.etf.content_service.services;

import ba.unsa.etf.content_service.entity.File;
import ba.unsa.etf.content_service.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public List<File> getFilesByFileType(String fileType) {
        return fileRepository.findByFileType(fileType);
    }

    @Transactional
    public File createFile(File file) {
        return fileRepository.save(file);
    }

    @Transactional
    public File updateFile(Long id, File fileDetails) {
        Optional<File> existingFile = fileRepository.findById(id);
        if (existingFile.isPresent()) {
            File file = existingFile.get();
            // Update file details
            file.setFilePath(fileDetails.getFilePath());
            file.setFileType(fileDetails.getFileType());

            return fileRepository.save(file);
        }
        return null; // Return null if file not found
    }

    @Transactional
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    // Add other methods as needed
}