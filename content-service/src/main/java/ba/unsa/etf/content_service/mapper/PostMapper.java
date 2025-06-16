package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.PostDto;
import ba.unsa.etf.content_service.dto.UserDto;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.service.UserDataClientService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

  private static final Logger logger = LoggerFactory.getLogger(PostMapper.class);

  private final UserDataClientService userDataClientService;

  @Autowired
  public PostMapper(UserDataClientService userDataClientService) {
    this.userDataClientService = userDataClientService;
  }

  /**
   * Mapira Post entitet u PostDto, obogaćujući ga podacima o korisniku pozivom
   * UserDataClientService.
   */
  public PostDto toDto(Post postEntity) {
    if (postEntity == null) {
      logger.warn("Pokušaj mapiranja null Post entiteta u PostDto.");
      return null;
    }

    PostDto postDto = new PostDto();
    postDto.setPostId(postEntity.getPostId());
    postDto.setText(postEntity.getText());
    postDto.setPostDate(postEntity.getPostDate());
    postDto.setStatus(postEntity.getStatus());
    // Ako imaš imageUrl, dodaj i njega:
    // if (postEntity.getImageUrl() != null) { // Pretpostavka da Post entitet ima getImageUrl()
    //     postDto.setImageUrl(postEntity.getImageUrl());
    // }

    // Dohvati i postavi podatke o korisniku
    // Pretpostavka: Post entitet ima vezu na User entitet (barem s ID-om)
    if (postEntity.getUser() != null && postEntity.getUser().getId() != null) {
      Long userId = postEntity.getUser().getId();
      logger.debug(
          "Mapiranje posta ID: {}, dohvaćanje detalja za korisnika ID: {}",
          postEntity.getPostId(),
          userId);
      UserDto authorDetailsDto = userDataClientService.fetchUserById(userId);
      postDto.setUser(authorDetailsDto); // authorDetailsDto može biti stvarni user ili placeholder
    } else {
      logger.warn(
          "Post s ID: {} nema povezanog korisnika ili korisnik (entitet) nema ID. Postavljanje placeholder korisnika u DTO.",
          postEntity.getPostId());
      // Pozovi fetchUserById s null da dobiješ placeholder, ili direktno kreiraj placeholder ako je
      // metoda public u UserDataClientService
      postDto.setUser(userDataClientService.fetchUserById(null));
    }

    return postDto;
  }

  public List<PostDto> toDto(List<Post> postEntities) {
    if (postEntities == null || postEntities.isEmpty()) {
      return Collections.emptyList(); // Vrati immutable praznu listu
    }
    return postEntities.stream()
        .map(this::toDto) // Koristi this::toDto jer je toDto instancna metoda
        .collect(Collectors.toList());
  }

  /**
   * Mapira PostDto (koji dolazi npr. s frontenda za kreiranje posta) u Post entitet. OVA METODA NE
   * POSTAVLJA User ENTITET NA Post ENTITET. To je odgovornost PostService.createPost() metode, koja
   * će prvo dohvatiti/kreirati lokalnu User referencu.
   */
  public Post fromDto(PostDto incomingPostDto) {
    if (incomingPostDto == null) {
      logger.warn("Pokušaj mapiranja null PostDto u Post entitet.");
      return null;
    }

    Post postEntity = new Post();
    // postId se obično ne postavlja iz DTO-a pri kreiranju, jer ga baza generira.
    // Ako DTO sadrži postId (npr. za ažuriranje), možeš ga postaviti.
    // if (incomingPostDto.getPostId() != null) {
    //     postEntity.setPostId(incomingPostDto.getPostId());
    // }

    postEntity.setText(incomingPostDto.getText());
    postEntity.setPostDate(incomingPostDto.getPostDate());
    postEntity.setStatus(incomingPostDto.getStatus());
    // Ako imaš imageUrl, dodaj i njega:
    // if (incomingPostDto.getImageUrl() != null) { // Pretpostavka da PostDto ima getImageUrl()
    //     postEntity.setImageUrl(incomingPostDto.getImageUrl());
    // }

    // NAPOMENA: User entitet se NE postavlja ovdje.
    // PostService će iz incomingPostDto.getUser().getId() dohvatiti ID,
    // zatim će pronaći ili kreirati lokalni User entitet (referencu),
    // i onda taj lokalni User entitet postaviti na postEntity.setUser().

    return postEntity;
  }
}
