package io.scoreboard;

import lombok.Getter;

@Getter
public class IllegalScoreboardArgumentException extends IllegalArgumentException {

    private final ScoreboardError error;

    public IllegalScoreboardArgumentException(ScoreboardError error) {
        super(error.getMessage());
        this.error = error;
    }
}