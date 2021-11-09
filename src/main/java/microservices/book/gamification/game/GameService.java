package microservices.book.gamification.game;

import lombok.Value;
import microservices.book.gamification.challenge.ChallangeSolvedDto;
import microservices.book.gamification.game.domain.BadgeType;

import java.util.List;

public interface GameService {

    GameResult newAttemptForUser(ChallangeSolvedDto challange);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
