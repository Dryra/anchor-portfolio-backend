package net.dryra.anchorbackend.collection;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reflection_collections")
public class ReflectionCollectionEntity {

    @Id
    @Column(length = 80, nullable = false)
    private String id;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(length = 255)
    private String subtitle;

    @Column(length = 100, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private boolean premium;

    @Column(nullable = false)
    private boolean seasonal;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "available_from")
    private LocalDate availableFrom;

    @Column(name = "available_until")
    private LocalDate availableUntil;

    @OneToMany(
            mappedBy = "collection",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ReflectionCollectionTranslationEntity> translations =
            new HashSet<>();

    protected ReflectionCollectionEntity() {
        // Required by JPA
    }

    public ReflectionCollectionEntity(
            String id,
            String title,
            String subtitle,
            String symbol,
            boolean premium,
            boolean seasonal,
            boolean active,
            int sortOrder,
            LocalDate availableFrom,
            LocalDate availableUntil
    ) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.symbol = symbol;
        this.premium = premium;
        this.seasonal = seasonal;
        this.active = active;
        this.sortOrder = sortOrder;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isPremium() {
        return premium;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public boolean isActive() {
        return active;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public LocalDate getAvailableUntil() {
        return availableUntil;
    }

    public void update(
            String title,
            String subtitle,
            String symbol,
            boolean premium,
            boolean seasonal,
            boolean active,
            int sortOrder,
            LocalDate availableFrom,
            LocalDate availableUntil
    ) {
        this.title = title;
        this.subtitle = subtitle;
        this.symbol = symbol;
        this.premium = premium;
        this.seasonal = seasonal;
        this.active = active;
        this.sortOrder = sortOrder;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public void changeSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    public Set<ReflectionCollectionTranslationEntity> getTranslations() {
        return translations;
    }

    public void addTranslation(
            ReflectionCollectionTranslationEntity translation
    ) {
        translations.add(translation);
    }

    public void clearTranslations() {
        translations.clear();
    }

    public void updateMetadata(
            String symbol,
            boolean premium,
            boolean seasonal,
            boolean active,
            int sortOrder,
            LocalDate availableFrom,
            LocalDate availableUntil
    ) {
        this.symbol = symbol;
        this.premium = premium;
        this.seasonal = seasonal;
        this.active = active;
        this.sortOrder = sortOrder;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
    }

    public void updateFallbackContent(
            String title,
            String subtitle
    ) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public Optional<ReflectionCollectionTranslationEntity> findTranslation(
            List<String> candidateLocales
    ) {
        for (String candidateLocale : candidateLocales) {
            Optional<ReflectionCollectionTranslationEntity> match =
                    translations.stream()
                            .filter(translation ->
                                    translation.getLocale()
                                            .equalsIgnoreCase(candidateLocale)
                            )
                            .findFirst();

            if (match.isPresent()) {
                return match;
            }
        }

        return Optional.empty();
    }

    public String getLocalizedTitle(
            List<String> candidateLocales
    ) {
        return findTranslation(candidateLocales)
                .map(ReflectionCollectionTranslationEntity::getTitle)
                .orElse(title);
    }

    public String getLocalizedSubtitle(
            List<String> candidateLocales
    ) {
        return findTranslation(candidateLocales)
                .map(ReflectionCollectionTranslationEntity::getSubtitle)
                .orElse(subtitle);
    }
}