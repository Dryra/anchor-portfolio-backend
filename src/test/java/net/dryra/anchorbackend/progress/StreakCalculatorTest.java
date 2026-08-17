package net.dryra.anchorbackend.progress;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class StreakCalculatorTest {

    private final StreakCalculator calculator = new StreakCalculator();

    @Test
    void calculatesCurrentAndLongestRunsAndIgnoresDuplicates() {
        LocalDate start = LocalDate.of(2026, 1, 1);
        StreakResult result = calculator.calculate(List.of(
                start, start, start.plusDays(1), start.plusDays(4),
                start.plusDays(5), start.plusDays(6)));

        assertThat(result.currentStreak()).isEqualTo(3);
        assertThat(result.longestStreak()).isEqualTo(3);
        assertThat(result.unlockedAchievements())
                .containsExactly(Achievement.FIRST_REFLECTION);
    }

    @Test
    void unlocksMilestonesFromTheLongestHistoricalStreak() {
        LocalDate start = LocalDate.of(2026, 2, 1);
        List<LocalDate> dates = java.util.stream.IntStream.range(0, 30)
                .mapToObj(start::plusDays)
                .toList();

        assertThat(calculator.calculate(dates).unlockedAchievements())
                .containsExactlyInAnyOrder(Achievement.FIRST_REFLECTION,
                        Achievement.SEVEN_DAY_STREAK, Achievement.THIRTY_DAY_STREAK);
    }

    @Test
    void handlesNoActivity() {
        assertThat(calculator.calculate(List.of()))
                .isEqualTo(new StreakResult(0, 0, null, java.util.Set.of()));
    }
}
