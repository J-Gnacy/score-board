package scoreboard;

import io.scoreboard.Match;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MatchTest {

    @Test
    void shouldCreateMatchWithZeroScores() {
        Match match = new Match("Mexico", "Canada");
        assertThat(match.homeTeamScore()).isZero();
        assertThat(match.awayTeamScore()).isZero();
    }

    @ParameterizedTest
    @MethodSource
    void shouldThrowWhenScoreIsNegative(int homeScore, int awayScore) {
        assertThatThrownBy(() -> new Match("Mexico", "Canada", homeScore, awayScore))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> shouldThrowWhenScoreIsNegative() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(0, -1),
                Arguments.of(-1, -1)
        );
    }

    @Test
    void shouldCalculateTotalScore() {
        Match match = new Match("Mexico", "Canada", 3, 2);
        assertThat(match.totalScore()).isEqualTo(5);
    }
}