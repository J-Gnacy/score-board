package scoreboard;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ScoreBoardTest {

    @Nested
    class StartMatch {
        @Test
        void shouldAddMatchToScoreboard() {}

        @Test
        void shouldStartWithScoreZeroZero() {}

        @Test
        void shouldStartWithCorrectTeamNames() {}

        @Test
        void shouldThrowWhenMatchAlreadyInProgress() {}

        @Test
        void shouldThrowWhenHomeTeamIsNull() {}

        @Test
        void shouldThrowWhenAwayTeamIsBlank() {}

        @Test
        void shouldThrowWhenTeamsAreTheSame() {}
    }

    @Nested
    class FinishMatch {
        @Test
        void shouldRemoveMatchFromScoreboard() {}

        @Test
        void shouldThrowWhenMatchNotFound() {}
    }

    @Nested
    class UpdateScore {
        @Test
        void shouldUpdateHomeAndAwayScore() {}

        @Test
        void shouldThrowWhenMatchNotFound() {}

        @Test
        void shouldThrowWhenScoreIsNegative() {}
    }

    @Nested
    class GetSummary {
        @Test
        void shouldReturnEmptyListWhenNoBoardsInProgress() {}

        @Test
        void shouldOrderByTotalScoreDescending() {}

        @Test
        void shouldOrderByMostRecentWhenTotalScoreEqual() {}

        @ParameterizedTest
        @MethodSource
        void shouldReturnMatchesInCorrectOrder(int index, String homeTeam, String awayTeam,
                                               int homeScore, int awayScore) {}

        static Stream<Arguments> shouldReturnMatchesInCorrectOrder() {
            return Stream.of(
                    Arguments.of(0, "Uruguay",   "Italy",     6, 6),
                    Arguments.of(1, "Spain",     "Brazil",    10, 2),
                    Arguments.of(2, "Mexico",    "Canada",    0, 5),
                    Arguments.of(3, "Argentina", "Australia", 3, 1),
                    Arguments.of(4, "Germany",   "France",    2, 2)
            );
        }
    }
}
