package microservices.book.gamification.game;

import microservices.book.gamification.game.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {

    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);
}
