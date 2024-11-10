package mk.ukim.finki.thesis.persistence.repository;

import mk.ukim.finki.thesis.persistence.model.SearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchQueryRepository extends JpaRepository<SearchQuery, Long> {
}
