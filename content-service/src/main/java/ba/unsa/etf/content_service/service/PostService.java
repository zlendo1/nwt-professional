package ba.unsa.etf.content_service.service; // Prilagodi ako je tvoj paket drugačiji

import ba.unsa.etf.content_service.dto.PostDto;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User; // Tvoj User ENTITET iz content-service (samo s ID-om)
import ba.unsa.etf.content_service.mapper.PostMapper;
import ba.unsa.etf.content_service.repository.PostRepository;
import ba.unsa.etf.content_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
// Uklonjen nepotreban import za Collectors ako se ne koristi direktno ovdje
// import java.util.stream.Collectors;

@Service
public class PostService {

  private static final Logger logger = LoggerFactory.getLogger(PostService.class);

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final PostMapper postMapper;

  @Autowired
  public PostService(
          PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.postMapper = postMapper;
  }

  @Transactional(readOnly = true)
  public List<PostDto> getAllPosts() {
    logger.info("Dohvaćanje svih postova.");
    List<Post> posts = postRepository.findAll();
    return postMapper.toDto(posts); // PostMapper.toDto će obogatiti svaki PostDto s UserDto iz user-management-service
  }

  @Transactional(readOnly = true)
  public Optional<PostDto> getPostById(Long id) {
    logger.info("Dohvaćanje posta s ID: {}", id);
    return postRepository.findById(id).map(postMapper::toDto); // postMapper::toDto obogaćuje s UserDto
  }

  @Transactional
  public PostDto createPost(PostDto postDto) {
    logger.info("Kreiranje novog posta na temelju DTO."); // Malo skraćena log poruka
    if (postDto == null) {
      logger.error("Pokušaj kreiranja posta s null PostDto objektom.");
      throw new IllegalArgumentException("PostDto ne može biti null.");
    }

    // postMapper.fromDto() mapira osnovna polja (text, postDate, status)
    // NE postavlja User entitet, to radimo ručno ispod.
    Post post = postMapper.fromDto(postDto);

    Long userIdFromDto = null;
    if (postDto.getUser() != null && postDto.getUser().getId() != null) {
      userIdFromDto = postDto.getUser().getId();
      logger.info("ID korisnika iz dolaznog PostDto (postDto.getUser().getId()): {}", userIdFromDto);
    } else {
      logger.error("ID korisnika nije pronađen u PostDto (postDto.getUser() ili postDto.getUser().getId() je null).");
      throw new IllegalArgumentException("Korisnički ID mora biti priložen unutar 'user' objekta u PostDto.");
    }

    final Long finalUserIdToFetch = userIdFromDto;

    // Pokušaj pronaći lokalni User entitet. Ako ne postoji, kreiraj novu referencu samo s ID-jem.
    User authorEntity = userRepository.findById(finalUserIdToFetch)
            .orElseGet(() -> {
              logger.info("Lokalni User entitet (referenca) za ID: {} ne postoji. Kreiram novu referencu.", finalUserIdToFetch);
              User newUserReference = new User(finalUserIdToFetch); // Koristi konstruktor s ID-om
              return userRepository.save(newUserReference); // Spremi novu referencu u lokalnu bazu
            });

    // Logiramo samo ID jer naš lokalni User entitet (authorEntity) sada ima samo ID
    logger.info("Osigurana lokalna User referenca (entitet) u content-service bazi: ID={}", authorEntity.getId());

    post.setUser(authorEntity); // Postavi lokalnu User referencu (samo s ID-om) na Post entitet

    Post savedPost = postRepository.save(post);
    logger.info("Post uspješno spremljen s ID: {}", savedPost.getPostId());

    // postMapper.toDto() će sada uzeti savedPost, iz njega post.getUser().getId(),
    // pozvati UserDataClientService.fetchUserById() s tim ID-om,
    // dobiti UserDto s punim detaljima (firstName, lastName, itd.) od user-management-service,
    // i ugnijezditi taj puni UserDto unutar PostDto koji se vraća.
    return postMapper.toDto(savedPost);
  }

  @Transactional
  public void deletePost(Long id) {
    if (!postRepository.existsById(id)) {
      logger.warn("Pokušaj brisanja nepostojećeg posta s ID: {}", id);
      // Možeš odlučiti hoćeš li baciti iznimku ili samo ne raditi ništa
      // throw new EntityNotFoundException("Post s ID: " + id + " nije pronađen.");
      return;
    }
    logger.info("Brisanje posta s ID: {}", id);
    postRepository.deleteById(id);
  }
}