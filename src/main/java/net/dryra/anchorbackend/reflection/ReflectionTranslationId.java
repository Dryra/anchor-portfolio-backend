package net.dryra.anchorbackend.reflection;

import java.io.Serializable;
import java.util.Objects;

public class ReflectionTranslationId implements Serializable {

    private Long reflection;
    private String locale;

    public ReflectionTranslationId() {
    }

    public ReflectionTranslationId(
            Long reflection,
            String locale
    ) {
        this.reflection = reflection;
        this.locale = locale;
    }

    public Long getReflection() {
        return reflection;
    }

    public String getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ReflectionTranslationId that)) {
            return false;
        }

        return Objects.equals(reflection, that.reflection)
                && Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reflection, locale);
    }
}
