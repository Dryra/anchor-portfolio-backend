package net.dryra.anchorbackend.reflection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import net.dryra.anchorbackend.collection.ReflectionCollectionEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "reflections")
public class ReflectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Legacy English fallback value.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collection_id", nullable = false)
    private ReflectionCollectionEntity collection;

    @Column(nullable = false)
    private boolean premium;

    @Column(name = "daily_eligible", nullable = false)
    private boolean dailyEligible;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @OneToMany(
            mappedBy = "reflection",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ReflectionTranslationEntity> translations =
            new HashSet<>();

    protected ReflectionEntity() {
    }

    public ReflectionEntity(
            String text,
            ReflectionCollectionEntity collection,
            boolean premium,
            boolean dailyEligible,
            boolean active,
            int sortOrder
    ) {
        this.text = text;
        this.collection = collection;
        this.premium = premium;
        this.dailyEligible = dailyEligible;
        this.active = active;
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ReflectionCollectionEntity getCollection() {
        return collection;
    }

    public boolean isPremium() {
        return premium;
    }

    public boolean isDailyEligible() {
        return dailyEligible;
    }

    public boolean isActive() {
        return active;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public Set<ReflectionTranslationEntity> getTranslations() {
        return translations;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCollection(
            ReflectionCollectionEntity collection
    ) {
        this.collection = collection;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDailyEligible(boolean dailyEligible) {
        this.dailyEligible = dailyEligible;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Optional<ReflectionTranslationEntity> findTranslation(
            List<String> candidateLocales
    ) {
        for (String candidateLocale : candidateLocales) {
            Optional<ReflectionTranslationEntity> match =
                    translations.stream()
                            .filter(translation ->
                                    translation.getLocale()
                                            .equalsIgnoreCase(
                                                    candidateLocale
                                            )
                            )
                            .findFirst();

            if (match.isPresent()) {
                return match;
            }
        }

        return Optional.empty();
    }

    public String getLocalizedText(
            List<String> candidateLocales
    ) {
        return findTranslation(candidateLocales)
                .map(ReflectionTranslationEntity::getText)
                .orElse(text);
    }

    public void addTranslation(
            ReflectionTranslationEntity translation
    ) {
        translations.add(translation);
    }

    public void clearTranslations() {
        translations.clear();
    }

    public void updateMetadata(
            ReflectionCollectionEntity collection,
            boolean premium,
            boolean active,
            boolean dailyEligible,
            int sortOrder
    ) {
        this.collection = collection;
        this.premium = premium;
        this.active = active;
        this.dailyEligible = dailyEligible;
        this.sortOrder = sortOrder;
    }

    public void updateFallbackText(String text) {
        this.text = text;
    }
}