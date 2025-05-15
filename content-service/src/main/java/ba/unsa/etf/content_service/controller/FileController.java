package ba.unsa.etf.content_service.controller;

import ba.unsa.etf.content_service.dto.FileDto;
import ba.unsa.etf.content_service.entity.File;
import ba.unsa.etf.content_service.mapper.FileMapper;
import ba.unsa.etf.content_service.service.FileService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {

  private final FileService fileService;
  private final FileMapper fileMapper; // Dodajemo instancu FileMappera

  @Autowired
  public FileController(FileService fileService, FileMapper fileMapper) {
    this.fileService = fileService;
    this.fileMapper = fileMapper; // Kroz konstruktor uvozimo FileMapper
  }

  // GET all files
  @GetMapping
  public ResponseEntity<List<FileDto>> getAllFiles() {
    List<File> files = fileService.getAllFiles(); // Dohvatanje svih fajlova kao entiteta
    List<FileDto> fileDtos =
        fileMapper.toDto(files); // Mapiranje entiteta u DTO (instanciranje mappera)
    return new ResponseEntity<>(fileDtos, HttpStatus.OK); // Vraćanje lista DTO objekata
  }

  // GET file by ID
  @GetMapping("/{id}")
  public ResponseEntity<FileDto> getFileById(@PathVariable("id") Long id) {
    Optional<File> file = fileService.getFileById(id); // Dohvatanje fajla po ID
    return file.map(
            value ->
                new ResponseEntity<>(
                    fileMapper.toDto(value), HttpStatus.OK)) // Instanciranje mappera
        .orElseGet(
            () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Vraćanje 404 ako fajl ne postoji
  }

  // POST create a new file
  @PostMapping
  public ResponseEntity<FileDto> createFile(@RequestBody FileDto fileDto) {
    File file = fileMapper.fromDto(fileDto); // Mapiranje DTO u entitet
    File createdFile = fileService.createFile(file); // Kreiranje fajla putem servisa
    FileDto createdFileDto = fileMapper.toDto(createdFile); // Mapiranje natrag u DTO
    return new ResponseEntity<>(
        createdFileDto, HttpStatus.CREATED); // Vraćanje kreiranog fajla u DTO formatu
  }

  // DELETE file by ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFile(@PathVariable("id") Long id) {
    fileService.deleteFile(id); // Pozivanje servisa za brisanje fajla
    return new ResponseEntity<>(
        HttpStatus.NO_CONTENT); // Vraćanje odgovora bez sadržaja (no content)
  }
}
