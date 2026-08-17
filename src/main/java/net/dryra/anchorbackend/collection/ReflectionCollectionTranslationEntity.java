package net.dryra.anchorbackend.collection;

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
@Table(name = "reflection_collection_translations")
@IdClass(ReflectionCollectionTranslationId.class)
public class ReflectionCollectionTranslationEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collection_id", nullable = false)
    private ReflectionCollectionEntity collection;

    @Id
    @Column(length = 35, nullable = false)
    private String locale;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(length = 255)
    private String subtitle;

    protected ReflectionCollectionTranslationEntity() {
    }

    public ReflectionCollectionTranslationEntity(
            ReflectionCollectionEntity collection,
            String locale,
            String title,
            String subtitle
    ) {
        this.collection = collection;
        this.locale = normalizeLocale(locale);
        this.title = title;
        this.subtitle = subtitle;
    }

    private String normalizeLocale(String locale) {
        return locale
                .trim()
                .replace('_', '-')
                .toLowerCase(Locale.ROOT);
    }

    public ReflectionCollectionEntity getCollection() {
        return collection;
    }

    public String getLocale() {
        return locale;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void update(
            String title,
            String subtitle
    ) {
        this.title = title;
        this.subtitle = subtitle;
    }
}
