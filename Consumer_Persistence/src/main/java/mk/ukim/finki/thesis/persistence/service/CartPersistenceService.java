package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.common.helper.TimePeriodHelper.TimePeriod;
import mk.ukim.finki.thesis.persistence.enums.CartStatus;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartPersistenceService {

  private final CartRepository cartRepository;

  /**
   * Gets card by external card id or returns a new cart.
   *
   * @param externalCartId the cart id coming from the producers.
   *
   * @return existing or a new cart
   */
  public Cart getCart(Long externalCartId) {

    return cartRepository.findByExternalCartId(externalCartId).orElse(new Cart());
  }

  public Cart saveCart(Cart cart) {

    return cartRepository.save(cart);
  }

  /**
   * Gets a list of carts which have the {@link CartStatus#ABANDONED} status and their update time is in the provided
   * time period.
   *
   * @param timePeriod the time period for the range.
   *
   * @return list of abandoned carts.
   */
  public List<Cart> getAbandonedCartsInTimeRange(TimePeriod timePeriod) {
    return cartRepository.findCartsByStatusAndTimeOfUpdateBetween(CartStatus.ABANDONED,
                                                                  timePeriod.fromDate(),
                                                                  timePeriod.toDate());
  }
}
