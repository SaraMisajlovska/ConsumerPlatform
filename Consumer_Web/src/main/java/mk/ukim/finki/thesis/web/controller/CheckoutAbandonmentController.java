package mk.ukim.finki.thesis.web.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.spi.service.CheckoutAbandonmentService;
import mk.ukim.finki.thesis.spi.service.enumeration.TimePeriodType;
import mk.ukim.finki.thesis.spi.service.impl.CheckoutAbandonmentServiceImpl.ReasonAndProducts;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/abandon")
@CrossOrigin(origins = "http://localhost:5173")
public class CheckoutAbandonmentController {

  private final CheckoutAbandonmentService checkoutAbandonmentService;

  public record TimePeriodDto(LocalDateTime fromDate, LocalDateTime toDate, String type) {
  }

  @PostMapping("reasons-and-products")
  public Map<String, List<ReasonAndProducts>> getCancellationReasonAndTopProducts(
          @RequestBody TimePeriodDto timePeriod) {

    return checkoutAbandonmentService.getProductsForCancellationReason(timePeriod.fromDate,
                                                                       timePeriod.toDate,
                                                                       TimePeriodType.valueOf(timePeriod.type));
  }
}
