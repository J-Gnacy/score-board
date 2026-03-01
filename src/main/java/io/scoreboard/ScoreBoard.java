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
        final MatchKey key = findMatchKey(homeTeam, awayTeam);
        matches.remove(key);
    }

    public void updateScore(final String homeTeam, final String awayTeam, final int homeScore, final int awayScore) {
        validateTeamNames(homeTeam, awayTeam);
        final MatchKey key = findMatchKey(homeTeam, awayTeam);

        final Match current = matches.get(key);

        validateScore(current, homeScore, awayScore);

        matches.put(key, new Match(homeTeam, awayTeam, homeScore, awayScore));
    }

    public List<Match> getSummary() {
        return sortedByScoreAndRecency(new ArrayList<>(matches.values()));
    }

    private List<Match> sortedByScoreAndRecency(List<Match> matches) {
        Collections.reverse(matches);
        return matches.stream()
                .sorted(Comparator.comparingInt(Match::totalScore).reversed())
                .toList();
    }

    private MatchKey findMatchKey(final String homeTeam, final String awayTeam) {
        final MatchKey key = new MatchKey(homeTeam, awayTeam);

        validateMatchExists(key);

        return key;
    }

    private void validateMatchExists(final MatchKey key) {
        if (!matches.containsKey(key)) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.MATCH_NOT_FOUND, key.homeTeam(), key.awayTeam());
        }
    }

    private void validateNewMatch(final String homeTeam, final String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        validateSameNames(homeTeam, awayTeam);
        validateMatchProgress(homeTeam, awayTeam);
    }

    private void validateMatchProgress(String homeTeam, String awayTeam) {
        if (matches.containsKey(new MatchKey(homeTeam, awayTeam))) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.MATCH_ALREADY_IN_PROGRESS, homeTeam, awayTeam);
        }
    }

    private static void validateSameNames(final String homeTeam, final String awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.TEAMS_ARE_THE_SAME, homeTeam, awayTeam);
        }
    }

    private static void validateTeamNames(final String homeTeam, final String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.HOME_TEAM_INVALID);
        }
        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.AWAY_TEAM_INVALID);
        }
    }

    private static void validateScore(final Match current, final int homeScore, final int awayScore) {
        int currentHomeScore = current.homeTeamScore();
        int currentAwayScore = current.awayTeamScore();

        if (currentHomeScore == homeScore && currentAwayScore == awayScore) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.SCORE_IS_THE_SAME, currentHomeScore, currentAwayScore, homeScore, awayScore);
        }

        if (homeScore < currentHomeScore || awayScore < currentAwayScore) {
            throw new IllegalScoreboardArgumentException(ScoreboardError.SCORE_IS_LOWER_THAN_CURRENT,  currentHomeScore, currentAwayScore, homeScore, awayScore);
        }
    }
}
