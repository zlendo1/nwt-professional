package ba.unsa.etf.content_service.config;

import ba.unsa.etf.content_service.entity.Comment;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.repository.CommentRepository;
import ba.unsa.etf.content_service.repository.PostRepository;
import ba.unsa.etf.content_service.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  @Autowired private UserRepository userRepository;

  @Autowired private PostRepository postRepository;

  @Autowired private CommentRepository commentRepository;

  private final Random random = new Random();

  @Override
  public void run(String... args) throws Exception {
    if (userRepository.count() == 0) {
      logger.info("Učitavam test podatke u bazu...");
      loadTestData();
      logger.info("Test podaci uspešno učitani!");
      logStatistics();
    } else {
      logger.info("Test podaci već postoje u bazi.");
      logStatistics();
    }
  }

  private void loadTestData() {
    // 1. Kreiraj test korisnike
    List<User> users = createTestUsers();
    List<User> savedUsers = userRepository.saveAll(users);

    // 2. Kreiraj test postove
    List<Post> posts = createTestPosts(savedUsers);
    List<Post> savedPosts = postRepository.saveAll(posts);

    // 3. Kreiraj test komentare
    List<Comment> comments = createTestComments(savedUsers, savedPosts);
    commentRepository.saveAll(comments);
  }

  private List<User> createTestUsers() {
    List<User> users = new ArrayList<>();

    String[][] userData = {
      {"marko.petrovic", "Petrović", "marko.petrovic@example.com", "password123"},
      {"ana.jovanovic", "Jovanović", "ana.jovanovic@example.com", "password123"},
      {"stefan.nikolic", "Nikolić", "stefan.nikolic@example.com", "password123"},
      {"milica.stojanovic", "Stojanović", "milica.stojanovic@example.com", "password123"},
      {"aleksandar.mitrovic", "Mitrović", "aleksandar.mitrovic@example.com", "password123"},
      {"jovana.radic", "Radić", "jovana.radic@example.com", "password123"},
      {"nikola.vukovic", "Vuković", "nikola.vukovic@example.com", "password123"},
      {"tamara.jovanovic", "Jovanović", "tamara.jovanovic@example.com", "password123"}
    };

    for (String[] data : userData) {
      User user = new User();
      user.setUsername(data[0]);
      user.setUserlastname(data[1]);
      user.setEmail(data[2]);
      user.setPassword(data[3]); // U realnom projektu bi trebalo hashirati
      user.setRegdate(LocalDate.now().minusDays(random.nextInt(365)));
      users.add(user);
    }

    return users;
  }

  private List<Post> createTestPosts(List<User> users) {
    List<Post> posts = new ArrayList<>();

    String[] postTexts = {
      "Danas sam završio odličan projekat na poslu! Timski rad je ključ uspeha. 💪 #teamwork #success",
      "Podelim sa vama sjajnu vest - dobio sam promociju! Hvala svima koji su me podržali. 🎉",
      "Upravo sam završio kurs za Spring Boot. Preporučujem svima koji se bave Java developmentom! #java #springboot",
      "Networking je jako važan u karijeri. Danas sam upoznao mnoge zanimljive ljude na konferenciji. #networking",
      "Radim na novom projektu koji koristi mikroservise. Izazovno, ali jako zanimljivo! #microservices #development",
      "Podelim svoja iskustva sa remote radom. Ključ je u dobroj organizaciji vremena. #remotework #productivity",
      "Tech meetup večeras bio je fantastičan! Naučio sam mnogo novih stvari. #techmeetup #learning",
      "Mentor mi je dao odličan savet: uvek učiti nešto novo svaki dan. Kako vi pristupate učenju? #mentorship #growth",
      "Implementirao sam REST API za content servis. Sve radi kako treba! #restapi #backend",
      "Debugging session koji je trajao 3 sata, ali konačno sam našao grešku! 🐛➡️✅ #debugging #programming",
      "Kod review je završen - sve pripremke su prihvaćene! Idemo dalje sa projektom. #codereview #git",
      "Testiranje komunikacije između mikroservisa - sve funkcionalnosti rade besprekorno! #testing #qa",
      "Nova verzija aplikacije je deploy-ovana na production! 🚀 #deployment #production",
      "Database optimizacija je završena - performanse su značajno poboljšane! #database #optimization",
      "API dokumentacija je ažurirana. Sada je mnogo lakše integrirati se sa našim servisom. #documentation #api"
    };

    String[] jobPosts = {
      "🔍 TRAŽIMO: Senior Java Developer\n\n📍 Lokacija: Sarajevo/Remote\n💰 Plata: Konkurentna\n\n✅ Potrebno:\n- Spring Boot, mikroservisi\n- PostgreSQL, Redis\n- 5+ godina iskustva\n\n📧 Pošaljite CV na careers@company.com\n\n#job #java #sarajevo",
      "🚀 STARTUP POZICIJA: Full Stack Developer\n\n📍 Lokacija: Remote\n💰 Equity + plata\n\n✅ Stack:\n- React + Node.js\n- MongoDB\n- AWS\n\n🎯 Tražimo passionate developera za fintech startup!\n\n#job #fullstack #startup #fintech",
      "💼 OTVORENA POZICIJA: Frontend Developer\n\n📍 Lokacija: Banja Luka\n⏰ Fleksibilno radno vreme\n\n✅ Tehnologije:\n- React, TypeScript\n- Tailwind CSS\n- Jest, Cypress\n\n🌟 Odličan tim, mlada kompanija!\n\n#job #frontend #react #banjaluka"
    };

    // Kreiraj obične postove
    for (int i = 0; i < 20; i++) {
      Post post = new Post();
      post.setUser(users.get(random.nextInt(users.size())));
      post.setText(postTexts[i % postTexts.length]);
      post.setPostDate(LocalDate.now().minusDays(random.nextInt(30)));
      post.setStatus("ACTIVE");
      posts.add(post);
    }

    // Kreiraj job postove
    for (int i = 0; i < 3; i++) {
      Post post = new Post();
      post.setUser(
          users.get(
              random.nextInt(
                  Math.min(3, users.size())))); // Samo prva 3 korisnika objavljuju poslove
      post.setText(jobPosts[i]);
      post.setPostDate(LocalDate.now().minusDays(random.nextInt(7)));
      post.setStatus("ACTIVE");
      posts.add(post);
    }

    // Kreiraj nekoliko draft postova
    for (int i = 0; i < 2; i++) {
      Post post = new Post();
      post.setUser(users.get(random.nextInt(users.size())));
      post.setText("Ovo je draft post koji još nije objavljen...");
      post.setPostDate(LocalDate.now());
      post.setStatus("DRAFT");
      posts.add(post);
    }

    return posts;
  }

  private List<Comment> createTestComments(List<User> users, List<Post> posts) {
    List<Comment> comments = new ArrayList<>();

    String[] commentTexts = {
      "Svaka čast! 👏",
      "Odličan post, hvala na deljenju!",
      "Potpuno se slažem sa tobom.",
      "Gde mogu da saznam više o ovome?",
      "Inspirativno! Hvala ti. 🙏",
      "Imao sam slično iskustvo.",
      "Korisne informacije, bookmark!",
      "Bravo za uspeh! 🎉",
      "Možeš li da podeliš više detalja?",
      "Super saveti, hvala!",
      "Ovo mi je baš trebalo!",
      "Odličan pristup problemu.",
      "Definitivno cu probati ovo.",
      "Thanks for sharing! 💪",
      "Veoma korisno za početnike.",
      "Slažem se 100%!",
      "Genijalano rešenje!",
      "Ovako nešto tražim već nedeljama.",
      "Bookmarked za kasnije! 📑",
      "Odličan tutorial, hvala!",
      "Interesantna perspektiva!",
      "Mogu li da postavim pitanje?",
      "Remindovalo me je na moje iskustvo.",
      "Hvala što deliš ovakve stvari!",
      "Veoma insightful post."
    };

    // Dodaj komentare na postove (ne na draft postove)
    List<Post> activePosts = posts.stream().filter(p -> "ACTIVE".equals(p.getStatus())).toList();

    for (Post post : activePosts) {
      int numComments = random.nextInt(6); // 0-5 komentara po postu

      for (int i = 0; i < numComments; i++) {
        Comment comment = new Comment();

        // Izbegni da autor komentariše svoj post (ponekad)
        User commentAuthor;
        if (random.nextBoolean() && users.size() > 1) {
          // 50% šanse da neko drugi komentariše
          do {
            commentAuthor = users.get(random.nextInt(users.size()));
          } while (commentAuthor.getId() != null
              && commentAuthor.getId().equals(post.getUser().getId()));
        } else {
          // 50% šanse da bilo ko komentariše (uključujući autora)
          commentAuthor = users.get(random.nextInt(users.size()));
        }

        comment.setUser(commentAuthor);
        comment.setPost(post);
        comment.setText(commentTexts[random.nextInt(commentTexts.length)]);

        // Komentar je napisan nakon posta
        LocalDate commentDate = post.getPostDate().plusDays(random.nextInt(5));
        if (commentDate.isAfter(LocalDate.now())) {
          commentDate = LocalDate.now();
        }
        comment.setPostdate(commentDate);

        comments.add(comment);
      }
    }

    return comments;
  }

  private void logStatistics() {
    long userCount = userRepository.count();
    long postCount = postRepository.count();
    long commentCount = commentRepository.count();

    logger.info("=== STATISTIKE BAZE PODATAKA ===");
    logger.info("👥 Korisnici: {}", userCount);
    logger.info("📝 Postovi: {}", postCount);
    logger.info("💬 Komentari: {}", commentCount);
    logger.info("===============================");

    if (postCount > 0) {
      logger.info("✅ Test podaci su spremni za testiranje komunikacije!");
      logger.info("🔗 Health check: http://localhost:8081/api/content/health");
      logger.info("🗄️  H2 Console: http://localhost:8081/h2-console");
    }
  }
}
