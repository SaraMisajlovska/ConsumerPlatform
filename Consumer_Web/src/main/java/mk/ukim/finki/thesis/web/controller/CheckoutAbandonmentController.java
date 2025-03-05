package mk.ukim.finki.thesis.web.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.common.enums.TimePeriodType;
import mk.ukim.finki.thesis.spi.service.CheckoutAbandonmentService;
import mk.ukim.finki.thesis.spi.service.impl.CheckoutAbandonmentServiceImpl.ReasonAndProducts;
import mk.ukim.finki.thesis.web.records.TimePeriodDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/abandon")
@CrossOrigin(origins = "http://localhost:5173")
public class CheckoutAbandonmentController {

  private final CheckoutAbandonmentService checkoutAbandonmentService;

  @PostMapping("reasons-and-products")
  public Map<String, List<ReasonAndProducts>> getCancellationReasonAndTopProducts(
          @RequestBody TimePeriodDto timePeriodDto) {

    return checkoutAbandonmentService.getProductsForCancellationReason(timePeriodDto.timePeriod(),
                                                                       TimePeriodType.valueOf(timePeriodDto.type()));
  }
}
