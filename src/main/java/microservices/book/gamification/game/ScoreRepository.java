package microservices.book.gamification.game;

import microservices.book.gamification.game.domain.LeaderBoardRow;
import microservices.book.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends CrudRepository<ScoreCard, Long> {

    @Query("select sum(s.score) from ScoreCard s where s.userId = :userId group by s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") Long userId);

    @Query("select new microservices.book.gamification.game.domain.LeaderBoardRow(s.userId, sum(s.score)) " +
           "from ScoreCard s group by s.userId order by sum(s.score) desc")
    List<LeaderBoardRow> findFirst10();

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
