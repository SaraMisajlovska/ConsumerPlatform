package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "search_query")
public class SearchQuery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "query", nullable = false)
  private String query;

  @Column(name = "timestamp", nullable = false)
  private Long timestamp;

  // Getters and Setters
}
