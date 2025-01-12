package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.SearchWordFrequency;
import mk.ukim.finki.thesis.persistence.repository.SearchWordFrequencyRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class SearchWordFrequencyPersistenceService {

  private final SearchWordFrequencyRepository searchWordFrequencyRepository;

  public void updateWordFrequencies(String query) {
    String[] words = query.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");

    Arrays.stream(words)
            .filter(word -> !word.isEmpty())
            .forEach(this::incrementWordCount);

  }

  @Transactional
  public void incrementWordCount(String word) {
    SearchWordFrequency searchWordFrequency = searchWordFrequencyRepository.findByWord(word)
            .orElseGet(() -> newSearchWordFrequency(word));

    searchWordFrequency.setCount(searchWordFrequency.getCount() + 1);
    searchWordFrequencyRepository.save(searchWordFrequency);
  }

  @Transactional
  public SearchWordFrequency newSearchWordFrequency(String word) {

    SearchWordFrequency newWordFrequency = new SearchWordFrequency();
    newWordFrequency.setWord(word);
    newWordFrequency.setCount(0);
    return searchWordFrequencyRepository.save(newWordFrequency);
  }
}
