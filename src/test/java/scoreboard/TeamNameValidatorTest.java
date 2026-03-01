package scoreboard;

import io.scoreboard.IllegalScoreboardArgumentException;
import io.scoreboard.TeamNameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TeamNameValidatorTest {

    @ParameterizedTest
    @MethodSource("scoreboard.TeamNameValidatorTestData#invalidTeamNames")
    void shouldThrowWhenTeamNameIsInvalid(String homeTeam, String awayTeam) {
        assertThatThrownBy(() -> TeamNameValidator.validateTeamNames(homeTeam, awayTeam))
                .isInstanceOf(IllegalScoreboardArgumentException.class)
                .hasMessageContaining("cannot be null or blank");
    }

    @Test
    void shouldThrowWhenTeamsAreTheSame() {
        assertThatThrownBy(() -> TeamNameValidator.validateSameNames("Mexico", "Mexico"))
                .isInstanceOf(IllegalScoreboardArgumentException.class)
                .hasMessageContaining("Mexico");
    }
}
