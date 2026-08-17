package net.dryra.anchorbackend.collection;

import java.time.LocalDate;
import java.util.List;
import net.dryra.anchorbackend.reflection.ReflectionResponse;

public record CollectionDetailResponse(
        String id,
        String title,
        String subtitle,
        String symbol,
        boolean premium,
        boolean seasonal,
        LocalDate availableFrom,
        LocalDate availableUntil,
        List<ReflectionResponse> reflections
) {
}
