package microservices.book.gamification.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BadgeType {
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),

    FIRST_WON("First Time"),
    LUCKY_NUMBER("Lucky Number");

    private final String description;
}

