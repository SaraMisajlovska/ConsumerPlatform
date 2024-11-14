package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "search_log", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "query", "time_of_searching"})})
public class SearchLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "query", nullable = false)
  private String query;

  @Column(name = "time_of_searching", nullable = false)
  private LocalDateTime timeOfSearching;
}
