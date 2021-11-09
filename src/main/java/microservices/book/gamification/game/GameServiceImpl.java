package microservices.book.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.challenge.ChallangeSolvedDto;
import microservices.book.gamification.game.badgeprocessors.BadgeProcessor;
import microservices.book.gamification.game.domain.BadgeCard;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(ChallangeSolvedDto challange) {
        if(challange.isCorrect()) {
            ScoreCard scoreCard = new ScoreCard(challange.getUserId(), challange.getAttemptId());
            scoreRepository.save(scoreCard);
            log.info("User {} scored {} points for attempt id{}", challange.getUserAlias(), scoreCard.getScore(), scoreCard.getAttemptId());
            List<BadgeCard> badgeCards = processForBadges(challange);
            return new GameResult(scoreCard.getScore(), badgeCards.stream().map(BadgeCard::getBadgeType).collect(Collectors.toList()));
        }
        else {
            log.info("Attempt id {} is not correct. User {} does not get score", challange.getAttemptId(),challange.getUserAlias());
            return new GameResult(0, List.of());
        }
    }

    private List<BadgeCard> processForBadges(final ChallangeSolvedDto solvedChallenge) {
        Optional<Integer> optTotalScore = scoreRepository.getTotalScoreForUser(solvedChallenge.getUserId());
        if(optTotalScore.isEmpty()) return Collections.emptyList();
        int totalScore = optTotalScore.get();
        List<ScoreCard> scoreCardList = scoreRepository.findByUserIdOrderByScoreTimestampDesc(solvedChallenge.getUserId());
        Set<BadgeType> alreadyGotBadges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(solvedChallenge.getUserId())
                .stream().map(BadgeCard::getBadgeType).collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream().filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, solvedChallenge)
                ).flatMap(Optional::stream)
                        .map(badgeType -> new BadgeCard(solvedChallenge.getUserId(), badgeType)
                        )
                        .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}
