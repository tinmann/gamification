package microservices.book.gamification.game;

import lombok.RequiredArgsConstructor;
import microservices.book.gamification.challenge.ChallangeSolvedDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postResult(@RequestBody ChallangeSolvedDto dto) {
        gameService.newAttemptForUser(dto);
    }
}
