package net.dryra.anchorbackend.progress;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/** Pure domain logic: duplicate events on the same day count only once. */
public class StreakCalculator {

    public StreakResult calculate(Collection<LocalDate> reflectionDates) {
        List<LocalDate> dates = reflectionDates.stream()
                .distinct()
                .sorted(Comparator.naturalOrder())
                .toList();

        if (dates.isEmpty()) {
            return new StreakResult(0, 0, null, Set.of());
        }

        int run = 1;
        int longest = 1;
        for (int index = 1; index < dates.size(); index++) {
            run = dates.get(index - 1).plusDays(1).equals(dates.get(index))
                    ? run + 1
                    : 1;
            longest = Math.max(longest, run);
        }

        int current = run;
        EnumSet<Achievement> achievements = EnumSet.noneOf(Achievement.class);
        for (Achievement achievement : Achievement.values()) {
            if (longest >= achievement.requiredStreak()) {
                achievements.add(achievement);
            }
        }

        return new StreakResult(current, longest, dates.getLast(), Set.copyOf(achievements));
    }
}
