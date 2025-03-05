package mk.ukim.finki.thesis.web.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.common.enums.TimePeriodType;
import mk.ukim.finki.thesis.spi.service.ProductViewService;
import mk.ukim.finki.thesis.spi.service.ProductViewService.ViewsPerCategory;
import mk.ukim.finki.thesis.web.records.TimePeriodDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/view")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductViewController {
  private final ProductViewService productViewService;

  @PostMapping("product-views")
  public Map<String, List<ViewsPerCategory>> getCancellationReasonAndTopProducts(
          @RequestBody TimePeriodDto timePeriodDto) {

    return productViewService.getViewsPerPeriodByCategory(timePeriodDto.timePeriod(),
                                                          TimePeriodType.valueOf(timePeriodDto.type()));
  }
}
