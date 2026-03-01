package scoreboard;

import io.scoreboard.IllegalScoreboardArgumentException;
import io.scoreboard.Match;
import io.scoreboard.ScoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScoreBoardTest {

    private ScoreBoard board;

    @BeforeEach
    void setUp() {
        board = new ScoreBoard();
    }

    @Nested
    class StartMatch {
        @Test
        void shouldAddMatchToScoreboard() {
            board.startMatch("Mexico", "Canada");
            var summaryList = board.getSummary();
            assertThat(summaryList).hasSize(1);
        }

        @Test
        void shouldStartWithScoreZeroZero() {
            board.startMatch("Mexico", "Canada");
            Match match = board.getSummary().getFirst();
            assertThat(match.homeTeamScore()).isZero();
            assertThat(match.awayTeamScore()).isZero();
        }

        @Test
        void shouldStartWithCorrectTeamNames() {
            board.startMatch("Mexico", "Canada");
            Match match = board.getSummary().getFirst();
            assertThat(match.homeTeamName()).isEqualTo("Mexico");
            assertThat(match.awayTeamName()).isEqualTo("Canada");
        }

        @Test
        void shouldThrowWhenMatchAlreadyInProgress() {
            board.startMatch("Mexico", "Canada");
            assertThatThrownBy(() -> board.startMatch("Mexico", "Canada"))
                    .isInstanceOf(IllegalScoreboardArgumentException.class);
        }

        @Test
        void shouldThrowWhenTeamsAreTheSame() {
            assertThatThrownBy(() -> board.startMatch("Mexico", "Mexico"))
                    .isInstanceOf(IllegalScoreboardArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource("scoreboard.TeamNameValidatorTestData#invalidTeamNames")
        void shouldThrowWhenTeamNameIsInvalid(String homeTeam, String awayTeam) {
            assertThatThrownBy(() -> board.startMatch(homeTeam, awayTeam))
                    .isInstanceOf(IllegalScoreboardArgumentException.class)
                    .hasMessageContaining("cannot be null or blank");
        }
    }

    @Nested
    class FinishMatch {
        @Test
        void shouldRemoveMatchFromScoreboard() {
            board.startMatch("Mexico", "Canada");
            board.finishMatch("Mexico", "Canada");
            assertThat(board.getSummary()).isEmpty();
        }

        @Test
        void shouldThrowWhenMatchNotFound() {
            assertThatThrownBy(() -> board.finishMatch("Mexico", "Canada"))
                    .isInstanceOf(IllegalScoreboardArgumentException.class);
        }

        @Test
        void shouldRemoveOnlyFinishedMatch() {
            board.startMatch("Mexico", "Canada");
            board.startMatch("Spain", "Brazil");
            board.finishMatch("Mexico", "Canada");
            assertThat(board.getSummary()).hasSize(1);
            assertThat(board.getSummary().getFirst().homeTeamName()).isEqualTo("Spain");
        }

        @Test
        void shouldAllowRestartingFinishedMatch() {
            board.startMatch("Mexico", "Canada");
            board.finishMatch("Mexico", "Canada");
            board.startMatch("Mexico", "Canada");
            assertThat(board.getSummary()).hasSize(1);
        }

        @ParameterizedTest
        @MethodSource("scoreboard.TeamNameValidatorTestData#invalidTeamNames")
        void shouldThrowWhenTeamNameIsInvalid(String homeTeam, String awayTeam) {
            assertThatThrownBy(() -> board.finishMatch(homeTeam, awayTeam))
                    .isInstanceOf(IllegalScoreboardArgumentException.class)
                    .hasMessageContaining("cannot be null or blank");
        }
    }

    @Nested
    class UpdateScore {
        @Test
        void shouldUpdateHomeAndAwayScore() {
            board.startMatch("Mexico", "Canada");
            board.updateScore("Mexico", "Canada", 2, 1);
            Match match = board.getSummary().getFirst();
            assertThat(match.homeTeamScore()).isEqualTo(2);
            assertThat(match.awayTeamScore()).isEqualTo(1);
        }

        @Test
        void shouldThrowWhenMatchNotFound() {
            assertThatThrownBy(() -> board.updateScore("Mexico", "Canada", 1, 0))
                    .isInstanceOf(IllegalScoreboardArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource
        void shouldThrowWhenScoreIsNegative(int homeScore, int awayScore) {
            board.startMatch("Mexico", "Canada");
            assertThatThrownBy(() -> board.updateScore("Mexico", "Canada", homeScore, awayScore))
                    .isInstanceOf(IllegalScoreboardArgumentException.class);
        }

        static Stream<Arguments> shouldThrowWhenScoreIsNegative() {
            return Stream.of(
                    Arguments.of(-1, 0),
                    Arguments.of(0, -5),
                    Arguments.of(-1, -5)
            );
        }

        @Test
        void shouldUpdateOnlySpecifiedMatch() {
            board.startMatch("Mexico", "Canada");
            board.startMatch("Spain", "Brazil");
            board.updateScore("Mexico", "Canada", 2, 1);
            Match spain = board.getSummary().stream()
                    .filter(m -> m.homeTeamName().equals("Spain"))
                    .findFirst()
                    .orElseThrow();
            assertThat(spain.homeTeamScore()).isZero();
            assertThat(spain.awayTeamScore()).isZero();
        }

        @ParameterizedTest
        @MethodSource
        void shouldThrowWhenScoreIsLowerThanCurrent(int homeScore, int awayScore) {
            board.startMatch("Mexico", "Canada");
            board.updateScore("Mexico", "Canada", 2, 1);
            assertThatThrownBy(() -> board.updateScore("Mexico", "Canada", homeScore, awayScore))
                    .isInstanceOf(IllegalScoreboardArgumentException.class);
        }

        static Stream<Arguments> shouldThrowWhenScoreIsLowerThanCurrent() {
            return Stream.of(
                    Arguments.of(1, 0),
                    Arguments.of(1, 1),
                    Arguments.of(2, 0)
            );
        }

        @Test
        void shouldThrowWhenScoreIsTheSameAsCurrent() {
            board.startMatch("Mexico", "Canada");
            board.updateScore("Mexico", "Canada", 1, 0);
            assertThatThrownBy(() -> board.updateScore("Mexico", "Canada", 1, 0))
                    .isInstanceOf(IllegalScoreboardArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource("scoreboard.TeamNameValidatorTestData#invalidTeamNames")
        void shouldThrowWhenTeamNameIsInvalid(String homeTeam, String awayTeam) {
            assertThatThrownBy(() -> board.updateScore(homeTeam, awayTeam, 1, 0))
                    .isInstanceOf(IllegalScoreboardArgumentException.class)
                    .hasMessageContaining("cannot be null or blank");
        }
    }

    @Nested
    class GetSummaryOnInitiallyEmptyBoard {
        @Test
        void shouldReturnEmptyListWhenNoBoardsInProgress() {
            assertThat(board.getSummary()).isEmpty();
        }

        @Test
        void shouldReturnUnmodifiableList() {
            board.startMatch("Mexico", "Canada");
            List<Match> summary = board.getSummary();
            assertThatThrownBy(() -> summary.add(new Match("Spain", "Brazil")))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        void shouldNotContainFinishedMatches() {
            board.startMatch("Mexico", "Canada");
            board.startMatch("Spain", "Brazil");
            board.finishMatch("Mexico", "Canada");
            assertThat(board.getSummary())
                    .extracting(Match::homeTeamName)
                    .containsOnly("Spain")
                    .doesNotContain("Mexico");
        }
    }

    @Nested
    class GetSummary {

        @BeforeEach
        void setUp() {
            board.startMatch("Mexico", "Canada");
            board.updateScore("Mexico", "Canada", 0, 5);
            board.startMatch("Spain", "Brazil");
            board.updateScore("Spain", "Brazil", 10, 2);
            board.startMatch("Germany", "France");
            board.updateScore("Germany", "France", 2, 2);
            board.startMatch("Uruguay", "Italy");
            board.updateScore("Uruguay", "Italy", 6, 6);
            board.startMatch("Argentina", "Australia");
            board.updateScore("Argentina", "Australia", 3, 1);
        }

        @Test
        void shouldOrderByTotalScoreDescending() {
            List<Match> summary = board.getSummary();

            assertThat(summary.get(0).totalScore()).isEqualTo(12);
            assertThat(summary.get(1).totalScore()).isEqualTo(12);
            assertThat(summary.get(2).totalScore()).isEqualTo(5);
            assertThat(summary.get(3).totalScore()).isEqualTo(4);
            assertThat(summary.get(4).totalScore()).isEqualTo(4);
        }

        @Test
        void shouldOrderByMostRecentWhenTotalScoreEqual() {
            List<Match> summary = board.getSummary();

            assertThat(summary.get(0).homeTeamName()).isEqualTo("Uruguay");
            assertThat(summary.get(1).homeTeamName()).isEqualTo("Spain");
            assertThat(summary.get(2).homeTeamName()).isEqualTo("Mexico");
            assertThat(summary.get(3).homeTeamName()).isEqualTo("Argentina");
            assertThat(summary.get(4).homeTeamName()).isEqualTo("Germany");
        }

        @ParameterizedTest
        @MethodSource
        void shouldReturnMatchesInCorrectOrder(int index, String homeTeam, String awayTeam,
                                               int homeScore, int awayScore) {
            Match match = board.getSummary().get(index);
            assertThat(match.homeTeamName()).isEqualTo(homeTeam);
            assertThat(match.awayTeamName()).isEqualTo(awayTeam);
            assertThat(match.homeTeamScore()).isEqualTo(homeScore);
            assertThat(match.awayTeamScore()).isEqualTo(awayScore);
        }

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
