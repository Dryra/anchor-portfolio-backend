package net.dryra.anchorbackend.reflection;

import java.util.List;

public record ReflectionResponse(
        long id,
        String text,
        String collectionId,
        boolean premium
) {
    public static ReflectionResponse from(
            ReflectionEntity reflection,
            List<String> candidateLocales
    ) {
        return new ReflectionResponse(
                reflection.getId(),
                reflection.getLocalizedText(candidateLocales),
                reflection.getCollection().getId(),
                reflection.isPremium()
        );
    }
}
