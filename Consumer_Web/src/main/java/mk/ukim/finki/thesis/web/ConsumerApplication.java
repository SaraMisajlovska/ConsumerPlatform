package mk.ukim.finki.thesis.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "mk.ukim.finki.thesis.web",
        "mk.ukim.finki.thesis.spi",
        "mk.ukim.finki.thesis.kafka"})
public class ConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}