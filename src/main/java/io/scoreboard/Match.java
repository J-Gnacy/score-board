package io.scoreboard;

public record Match(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {

    public Match {
        if (homeTeamScore < 0 || awayTeamScore < 0)
            throw new IllegalArgumentException("Score cannot be negative");
    }

    public Match(String homeTeamName, String awayTeamName) {
        this(homeTeamName, awayTeamName, 0, 0);
    }

    public int totalScore() {
        return homeTeamScore + awayTeamScore;
    }
}
