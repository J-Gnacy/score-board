package io.scoreboard;

public class TeamNameValidator {

    public static void validateSameNames(final String homeTeam, final String awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.TEAMS_ARE_THE_SAME, homeTeam, awayTeam);
        }
    }

    public static void validateTeamNames(final String homeTeam, final String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.HOME_TEAM_INVALID);
        }
        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.AWAY_TEAM_INVALID);
        }
    }
}
