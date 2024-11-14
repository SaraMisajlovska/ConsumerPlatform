package mk.ukim.finki.thesis.persistence.repository;

import mk.ukim.finki.thesis.persistence.model.SearchWordFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchWordFrequencyRepository extends JpaRepository<SearchWordFrequency, String> {

  Optional<SearchWordFrequency> findByWord(String word);
}
