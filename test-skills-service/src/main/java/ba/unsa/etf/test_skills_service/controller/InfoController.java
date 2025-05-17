package ba.unsa.etf.test_skills_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InfoController {

  @Autowired private Environment environment;

  @GetMapping("/info")
  public String getInstanceInfo() {
    String port = environment.getProperty("local.server.port");
    return "Hello from test-skills-service instance running on port: " + port;
  }
}
