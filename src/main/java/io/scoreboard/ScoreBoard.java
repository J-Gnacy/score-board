package io.scoreboard;

import java.util.*;

public class ScoreBoard {
    private final Map<MatchKey, Match> matches = new LinkedHashMap<>();

    public void startMatch(final String homeTeam, final String awayTeam) {
        validateNewMatch(homeTeam, awayTeam);

        matches.put(new MatchKey(homeTeam, awayTeam), new Match(homeTeam, awayTeam));
    }

    public void finishMatch(final String homeTeam, final String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        final MatchKey key = new MatchKey(homeTeam, awayTeam);
        if (!matches.containsKey(key))
            throw new IllegalArgumentException("Match not found");
        matches.remove(key);
    }

    public void updateScore(final String homeTeam, final String awayTeam, final int homeScore, final int awayScore) {
        validateTeamNames(homeTeam, awayTeam);
        final MatchKey key = new MatchKey(homeTeam, awayTeam);
        if (!matches.containsKey(key))
            throw new IllegalArgumentException("Match not found");

        final Match current = matches.get(key);
        if (current.homeTeamScore() == homeScore && current.awayTeamScore() == awayScore)
            throw new IllegalArgumentException("Score is the same as current");

        if (homeScore < current.homeTeamScore() || awayScore < current.awayTeamScore())
            throw new IllegalArgumentException("Score cannot be lower than current");

        matches.put(key, new Match(homeTeam, awayTeam, homeScore, awayScore));
    }

    public List<Match> getSummary() {
        final List<Match> insertionOrdered = new ArrayList<>(matches.values());
        Collections.reverse(insertionOrdered);
        return insertionOrdered.stream()
                .sorted(Comparator.comparingInt(Match::totalScore).reversed())
                .toList();
    }

    private void validateNewMatch(final String homeTeam, final String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        validateSameNames(homeTeam, awayTeam);
        validateMatchProgress(homeTeam, awayTeam);
    }

    private void validateMatchProgress(String homeTeam, String awayTeam) {
        if (matches.containsKey(new MatchKey(homeTeam, awayTeam)))
            throw new IllegalArgumentException("Match already in progress");
    }

    private void validateSameNames(final String homeTeam, final String awayTeam) {
        if (homeTeam.equals(awayTeam))
            throw new IllegalArgumentException("Home and away team cannot be the same");
    }

    private void validateTeamNames(final String homeTeam, final String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank())
            throw new IllegalArgumentException("Home team name cannot be null or blank");
        if (awayTeam == null || awayTeam.isBlank())
            throw new IllegalArgumentException("Away team name cannot be null or blank");
    }
}
