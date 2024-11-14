package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.User;
import mk.ukim.finki.thesis.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPersistenceService {

  private final UserRepository userRepository;

  public User getUser(Long externalUserId) {
    return userRepository.findByExternalUserId(externalUserId)
            .orElseGet(() -> createNewUser(externalUserId));
  }

  private User createNewUser(Long externalUserId) {
    User user = new User();

    user.setExternalUserId(externalUserId);

    return userRepository.save(user);
  }
}
