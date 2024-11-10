package mk.ukim.finki.thesis.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "mk.ukim.finki.thesis.persistence.model")
@EnableJpaRepositories(basePackages = "mk.ukim.finki.thesis.persistence.repository")
@SpringBootApplication(scanBasePackages = {
        "mk.ukim.finki.thesis.web",
        "mk.ukim.finki.thesis.spi",
        "mk.ukim.finki.thesis.persistence",
        "mk.ukim.finki.thesis.common",
        "mk.ukim.finki.thesis.kafkaconsumer"})
public class ConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}