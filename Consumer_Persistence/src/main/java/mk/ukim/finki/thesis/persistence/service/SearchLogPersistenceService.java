package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.SearchLog;
import mk.ukim.finki.thesis.persistence.repository.SearchLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchLogPersistenceService {

  private final SearchLogRepository searchLogRepository;
  private final SearchWordFrequencyPersistenceService searchWordFrequencyPersistenceService;

  public List<SearchLog> findAllInPeriod(LocalDateTime from, LocalDateTime to) {
    return searchLogRepository.findAllByTimeOfSearchingBetween(from, to);
  }

  public void save(SearchLog searchLog) {

    searchLogRepository.save(searchLog);
    searchWordFrequencyPersistenceService.updateWordFrequencies(searchLog.getQuery());
  }

}
