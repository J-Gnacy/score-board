package io.scoreboard;

import static io.scoreboard.TeamNameValidator.validateTeamNames;

public record Match(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {

    public Match {
        validateTeamNames(homeTeamName, awayTeamName);
        if (homeTeamScore < 0 || awayTeamScore < 0)
            throw new IllegalScoreboardArgumentException(ScoreboardError.SCORE_IS_NEGATIVE);
    }

    public Match(String homeTeamName, String awayTeamName) {
        this(homeTeamName, awayTeamName, 0, 0);
    }

    public int totalScore() {
        return homeTeamScore + awayTeamScore;
    }
}
