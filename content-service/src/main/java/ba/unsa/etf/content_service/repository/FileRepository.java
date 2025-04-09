package ba.unsa.etf.content_service.repository;

import ba.unsa.etf.content_service.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFileType(String fileType);
    List<File> findByFileName(String fileName);

}