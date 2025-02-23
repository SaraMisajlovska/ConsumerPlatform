package mk.ukim.finki.thesis.spi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.SearchLog;
import mk.ukim.finki.thesis.persistence.service.SearchLogPersistenceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchQueryService {

  private final SearchLogPersistenceService searchLogPersistenceService;

  public Map<String, Integer> getTopSearchedWordFrequencies(int limit, LocalDateTime fromDate, LocalDateTime toDate) {

    List<SearchLog> searchesInPeriod = searchLogPersistenceService.findAllInPeriod(fromDate, toDate);

    Map<String, Integer> wordFrequencies = searchesInPeriod.stream()
            .map(SearchLog::getQuery)
            .map(stringToWords())
            .flatMap(Arrays::stream)
            .collect(Collectors.toMap(
                    word -> word,
                    word -> 1,
                    Integer::sum
            ));

    Comparator<Map.Entry<String, Integer>> frequencyComparator =
            (word1, word2) -> word2.getValue().compareTo(word1.getValue());

    return wordFrequencies.entrySet().stream()
            .sorted(frequencyComparator)
            .limit(limit)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private static Function<String, String[]> stringToWords() {
    return query -> query.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");
  }
}
