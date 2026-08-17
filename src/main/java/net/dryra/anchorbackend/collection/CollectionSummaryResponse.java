package net.dryra.anchorbackend.collection;

import java.time.LocalDate;
import java.util.List;

public record CollectionSummaryResponse(
        String id,
        String title,
        String subtitle,
        String symbol,
        boolean premium,
        boolean seasonal,
        LocalDate availableFrom,
        LocalDate availableUntil
) {
    public static CollectionSummaryResponse from(
            ReflectionCollectionEntity collection,
            List<String> candidateLocales
    ) {
        return new CollectionSummaryResponse(
                collection.getId(),
                collection.getLocalizedTitle(candidateLocales),
                collection.getLocalizedSubtitle(candidateLocales),
                collection.getSymbol(),
                collection.isPremium(),
                collection.isSeasonal(),
                collection.getAvailableFrom(),
                collection.getAvailableUntil()
        );
    }
}