package io.scoreboard;

public record Match(
        String homeTeamName,
        String awayTeamName,
        int homeTeamScore,
        int awayTeamScore) {

    public Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, 0, 0);
    }
}
