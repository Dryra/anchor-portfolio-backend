package net.dryra.anchorbackend.collection;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReflectionCollectionRepository
        extends JpaRepository<ReflectionCollectionEntity, String> {

    @Override
    @EntityGraph(attributePaths = "translations")
    List<ReflectionCollectionEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "translations")
    Optional<ReflectionCollectionEntity> findById(String id);

    @EntityGraph(attributePaths = "translations")
    List<ReflectionCollectionEntity>
    findAllByActiveTrueOrderBySortOrderAsc();

    @EntityGraph(attributePaths = "translations")
    List<ReflectionCollectionEntity>
    findAllByOrderBySortOrderAsc();

    boolean existsBySortOrder(int sortOrder);
}