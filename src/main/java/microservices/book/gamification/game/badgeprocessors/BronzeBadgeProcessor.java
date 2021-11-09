package microservices.book.gamification.game.badgeprocessors;

import microservices.book.gamification.challenge.ChallangeSolvedDto;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public class BronzeBadgeProcessor implements BadgeProcessor{
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallangeSolvedDto solved) {
        return currentScore > 50 ? Optional.of(BadgeType.BRONZE) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
