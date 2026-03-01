package io.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ScoreboardError {
    MATCH_NOT_FOUND("Match not found"),
    MATCH_ALREADY_IN_PROGRESS("Match already in progress"),
    HOME_TEAM_INVALID("Home team name cannot be null or blank"),
    AWAY_TEAM_INVALID("Away team name cannot be null or blank"),
    TEAMS_ARE_THE_SAME("Home and away team cannot be the same"),
    SCORE_IS_NEGATIVE("Score cannot be negative"),
    SCORE_IS_LOWER_THAN_CURRENT("Score cannot be lower than current"),
    SCORE_IS_THE_SAME("Score is the same as current");

    private final String message;
}
