package net.dryra.anchorbackend.collection;

import java.util.List;
import net.dryra.anchorbackend.reflection.ReflectionResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/collections")
public class ReflectionCollectionController {

    private final ReflectionCollectionService collectionService;

    public ReflectionCollectionController(
            ReflectionCollectionService collectionService
    ) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity<List<CollectionSummaryResponse>>
    getCollections() {
        return ResponseEntity.ok()
                .cacheControl(
                        CacheControl.noCache()
                )
                .body(collectionService.getCollections());
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionDetailResponse>
    getCollection(
            @PathVariable String collectionId
    ) {
        return ResponseEntity.ok()
                .cacheControl(
                        CacheControl.noCache()
                )
                .body(
                        collectionService.getCollection(collectionId)
                );
    }

    @GetMapping("/{collectionId}/reflections")
    public ResponseEntity<List<ReflectionResponse>>
    getReflections(
            @PathVariable String collectionId
    ) {
        return ResponseEntity.ok()
                .cacheControl(
                        CacheControl.noCache()
                )
                .body(
                        collectionService.getReflections(collectionId)
                );
    }
}