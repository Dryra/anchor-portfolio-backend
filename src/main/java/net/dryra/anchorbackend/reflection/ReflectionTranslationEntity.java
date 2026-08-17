package net.dryra.anchorbackend.reflection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Locale;


@Entity
@Table(name = "reflection_translations")
@IdClass(ReflectionTranslationId.class)
public class ReflectionTranslationEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reflection_id", nullable = false)
    @JsonIgnore
    private ReflectionEntity reflection;

    @Id
    @Column(length = 35, nullable = false)
    private String locale;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    protected ReflectionTranslationEntity() {
    }

    public ReflectionTranslationEntity(
            ReflectionEntity reflection,
            String locale,
            String text
    ) {
        this.reflection = reflection;
        this.locale = normalizeLocale(locale);
        this.text = text;
    }

    private String normalizeLocale(String locale) {
        return locale
                .trim()
                .replace('_', '-')
                .toLowerCase(Locale.ROOT);
    }

    public ReflectionEntity getReflection() {
        return reflection;
    }

    public String getLocale() {
        return locale;
    }

    public String getText() {
        return text;
    }

    public void update(String text) {
        this.text = text;
    }
}