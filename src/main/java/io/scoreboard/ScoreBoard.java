package io.scoreboard;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreBoard {
    private final Map<MatchKey, Match> matches = new LinkedHashMap<>();

    public void startAGame(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException();
    }

    public void finishGame(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException();
    }

    public void updateScore(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException();
    }

    public void getGameSummary() {
        throw new UnsupportedOperationException();
    }
}
