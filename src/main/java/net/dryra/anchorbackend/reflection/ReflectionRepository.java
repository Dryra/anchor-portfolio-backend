package net.dryra.anchorbackend.reflection;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReflectionRepository
        extends JpaRepository<ReflectionEntity, Long> {

    @EntityGraph(attributePaths = {
            "translations",
            "collection"
    })
    List<ReflectionEntity>
    findAllByCollectionIdAndActiveTrueOrderBySortOrderAsc(
            String collectionId
    );

    @EntityGraph(attributePaths = {
            "translations",
            "collection"
    })
    List<ReflectionEntity>
    findAllByDailyEligibleTrueAndActiveTrueOrderByIdAsc();

    @EntityGraph(attributePaths = {
            "translations",
            "collection"
    })
    List<ReflectionEntity>
    findAllByOrderByCollection_IdAscSortOrderAscIdAsc();

    @EntityGraph(attributePaths = {
            "translations",
            "collection"
    })
    List<ReflectionEntity>
    findAllByCollection_IdOrderBySortOrderAscIdAsc(
            String collectionId
    );

    @Override
    @EntityGraph(attributePaths = {
            "translations",
            "collection"
    })
    Optional<ReflectionEntity> findById(Long id);

    @Query(value = """
        SELECT r.id
        FROM reflections r
        JOIN reflection_collections c
          ON c.id = r.collection_id
        WHERE r.active = true
          AND c.active = true
          AND r.premium = false
        ORDER BY RANDOM()
        LIMIT :limit
        """, nativeQuery = true)
    List<Long> findRandomActiveFreeReflectionIds(
            @Param("limit") int limit
    );

    @EntityGraph(attributePaths = {
            "translations",
            "collection"
    })
    List<ReflectionEntity> findAllByIdIn(
            List<Long> ids
    );
}