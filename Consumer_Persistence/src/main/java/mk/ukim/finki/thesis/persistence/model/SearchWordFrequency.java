package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "search_word_frequency")
public class SearchWordFrequency implements Comparable<SearchWordFrequency> {

  @Id
  @Column(name = "word", nullable = false, unique = true)
  private String word;

  @Column(name = "count", nullable = false)
  private Integer count;

  @Override
  public int compareTo(SearchWordFrequency o) {
    return Integer.compare(this.count, o.count);
  }
}