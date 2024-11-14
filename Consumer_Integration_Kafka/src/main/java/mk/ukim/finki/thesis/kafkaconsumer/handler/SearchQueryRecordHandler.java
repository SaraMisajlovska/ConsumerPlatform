package mk.ukim.finki.thesis.kafkaconsumer.handler;

import ecommerce.SearchQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import mk.ukim.finki.thesis.persistence.model.SearchLog;
import mk.ukim.finki.thesis.persistence.model.User;
import mk.ukim.finki.thesis.persistence.service.SearchLogPersistenceService;
import mk.ukim.finki.thesis.persistence.service.UserPersistenceService;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

import static mk.ukim.finki.thesis.kafkaconsumer.mapper.AvroObjectsMapper.mapTimestampToLocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchQueryRecordHandler implements RecordHandler {

  private final UserPersistenceService userPersistenceService;
  private final SearchLogPersistenceService searchLogPersistenceService;

  @Override
  public boolean handles(MessageKey key) {
    return MessageKey.SEARCH_QUERY.equals(key);
  }

  @Override
  public void handle(SpecificRecord record) {
    SearchQuery searchQuery = (SearchQuery) record;

    SearchLog searchLog = mapToSearchLog(searchQuery);

    searchLogPersistenceService.save(searchLog);
  }

  private SearchLog mapToSearchLog(SearchQuery searchQuery) {
    User user = userPersistenceService.getUser(searchQuery.getUserId());

    SearchLog searchLog = new SearchLog();
    searchLog.setUser(user);
    searchLog.setQuery(searchQuery.getQuery());
    searchLog.setTimeOfSearching(mapTimestampToLocalDateTime(searchQuery.getTimestamp()));
    return searchLog;
  }
}
