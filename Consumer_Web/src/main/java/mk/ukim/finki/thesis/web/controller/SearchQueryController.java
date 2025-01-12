package mk.ukim.finki.thesis.web.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.spi.service.SearchQueryService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchQueryController {

  private final SearchQueryService searchQueryService;

  public record SearchInputDto(int limit, LocalDateTime fromDate, LocalDateTime toDate) {
  }

  @PostMapping("top-words")
  public Map<String, Integer> search(@RequestBody SearchInputDto searchInputDto) {

    return searchQueryService.getTopSearchedWordFrequencies(searchInputDto.limit(),
                                                            searchInputDto.fromDate(),
                                                            searchInputDto.toDate());
  }
}
