package net.dryra.anchorbackend.collection;

import java.io.Serializable;
import java.util.Objects;

public class ReflectionCollectionTranslationId implements Serializable {

    private String collection;
    private String locale;

    public ReflectionCollectionTranslationId() {
    }

    public ReflectionCollectionTranslationId(
            String collection,
            String locale
    ) {
        this.collection = collection;
        this.locale = locale;
    }

    public String getCollection() {
        return collection;
    }

    public String getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ReflectionCollectionTranslationId that)) {
            return false;
        }

        return Objects.equals(collection, that.collection)
                && Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection, locale);
    }
}
