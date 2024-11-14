package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartPersistenceService {

  private final CartRepository cartRepository;

  public Cart getCart(Long externalCartId) {

    return cartRepository.findByExternalCartId(externalCartId).orElse(null);
  }

  public Cart saveCart(Cart cart) {

    return cartRepository.save(cart);
  }
}
