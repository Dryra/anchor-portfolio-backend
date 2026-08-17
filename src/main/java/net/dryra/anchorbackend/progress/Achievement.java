package net.dryra.anchorbackend.progress;

public enum Achievement {
    FIRST_REFLECTION(1),
    SEVEN_DAY_STREAK(7),
    THIRTY_DAY_STREAK(30);

    private final int requiredStreak;

    Achievement(int requiredStreak) {
        this.requiredStreak = requiredStreak;
    }

    public int requiredStreak() {
        return requiredStreak;
    }
}
