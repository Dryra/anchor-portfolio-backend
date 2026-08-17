package net.dryra.anchorbackend.collection;

import java.util.List;
import net.dryra.anchorbackend.common.ResourceNotFoundException;
import net.dryra.anchorbackend.localization.LocaleService;
import net.dryra.anchorbackend.reflection.ReflectionRepository;
import net.dryra.anchorbackend.reflection.ReflectionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReflectionCollectionService {

    private final ReflectionCollectionRepository collectionRepository;
    private final ReflectionRepository reflectionRepository;
    private final LocaleService localeService;


    public ReflectionCollectionService(
            ReflectionCollectionRepository collectionRepository,
            ReflectionRepository reflectionRepository,
            LocaleService localeService
    ) {
        this.collectionRepository = collectionRepository;
        this.reflectionRepository = reflectionRepository;
        this.localeService = localeService;
    }

    public List<CollectionSummaryResponse> getCollections() {
        List<String> candidateLocales =
                localeService.getCandidateLocales();

        return collectionRepository
                .findAllByActiveTrueOrderBySortOrderAsc()
                .stream()
                .map(collection ->
                        CollectionSummaryResponse.from(
                                collection,
                                candidateLocales
                        )
                )
                .toList();
    }

    public CollectionDetailResponse getCollection(
            String collectionId
    ) {
        List<String> candidateLocales =
                localeService.getCandidateLocales();

        ReflectionCollectionEntity collection =
                getCollectionEntity(collectionId);

        List<ReflectionResponse> reflections =
                reflectionRepository
                        .findAllByCollectionIdAndActiveTrueOrderBySortOrderAsc(
                                collectionId
                        )
                        .stream()
                        .map(reflection ->
                                ReflectionResponse.from(
                                        reflection,
                                        candidateLocales
                                )
                        )
                        .toList();

        return new CollectionDetailResponse(
                collection.getId(),
                collection.getLocalizedTitle(candidateLocales),
                collection.getLocalizedSubtitle(candidateLocales),
                collection.getSymbol(),
                collection.isPremium(),
                collection.isSeasonal(),
                collection.getAvailableFrom(),
                collection.getAvailableUntil(),
                reflections
        );
    }

    public List<ReflectionResponse> getReflections(
            String collectionId
    ) {
        List<String> candidateLocales =
                localeService.getCandidateLocales();

        getCollectionEntity(collectionId);

        return reflectionRepository
                .findAllByCollectionIdAndActiveTrueOrderBySortOrderAsc(
                        collectionId
                )
                .stream()
                .map(reflection ->
                        ReflectionResponse.from(
                                reflection,
                                candidateLocales
                        )
                )
                .toList();
    }

    private ReflectionCollectionEntity getCollectionEntity(
            String collectionId
    ) {
        return collectionRepository
                .findById(collectionId)
                .filter(ReflectionCollectionEntity::isActive)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Collection '%s' was not found."
                                        .formatted(collectionId)
                        )
                );
    }
}