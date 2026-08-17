package net.dryra.anchorbackend.progress;

import java.time.LocalDate;
import java.util.Set;

public record StreakResult(
        int currentStreak,
        int longestStreak,
        LocalDate lastReflectionDate,
        Set<Achievement> unlockedAchievements
) {
}
