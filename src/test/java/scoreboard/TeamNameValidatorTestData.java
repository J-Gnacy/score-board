package scoreboard;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class TeamNameValidatorTestData {

    static final String INVALID_TEAM_NAME = "  ";

    static Stream<Arguments> invalidTeamNames() {
        return Stream.of(
                Arguments.of(null, "Canada"),
                Arguments.of(INVALID_TEAM_NAME, "Canada"),
                Arguments.of("Mexico", null),
                Arguments.of("Mexico", INVALID_TEAM_NAME)
        );
    }
}
