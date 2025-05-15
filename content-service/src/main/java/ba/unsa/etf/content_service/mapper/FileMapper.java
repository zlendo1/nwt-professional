package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.FileDto;
import ba.unsa.etf.content_service.entity.File;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

  // Postoji li već toDto za pojedinačne entitete? Ako ne, definiraćemo
  public FileDto toDto(File file) {
    FileDto fileDto = new FileDto();
    fileDto.setFileId(file.getFileId());
    fileDto.setFileName(file.getFileName());
    fileDto.setFilePath(file.getFilePath());
    fileDto.setFileType(file.getFileType());
    fileDto.setPostId(file.getPost().getPostId()); // Dodajemo postId iz Post entiteta
    return fileDto;
  }

  // Dodajemo metodu za mapiranje liste
  public List<FileDto> toDto(List<File> files) {
    return files.stream()
        .map(this::toDto) // Pozivamo prethodno definiranu metodu za pojedinačne entitete
        .collect(Collectors.toList());
  }

  // Metoda za pretvaranje DTO u entitet (ako je potrebno)
  public File fromDto(FileDto fileDto) {
    File file = new File();
    file.setFileId(fileDto.getFileId());
    file.setFileName(fileDto.getFileName());
    file.setFilePath(fileDto.getFilePath());
    file.setFileType(fileDto.getFileType());
    // Dodajemo Post entitet prema postId (ako je potrebno)
    return file;
  }
}
