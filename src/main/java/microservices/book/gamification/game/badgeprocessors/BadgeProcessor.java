package microservices.book.gamification.game.badgeprocessors;

import microservices.book.gamification.challenge.ChallangeSolvedDto;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    Optional<BadgeType> processForOptionalBadge(
            int currentScore,
            List<ScoreCard> scoreCardList,
            ChallangeSolvedDto solved);

    BadgeType badgeType();
}
