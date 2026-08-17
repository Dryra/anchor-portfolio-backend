package net.dryra.anchorbackend.reflection;

import java.time.Duration;
import java.util.List;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reflections")
public class ReflectionController {

    private final DailyReflectionService dailyReflectionService;

    public ReflectionController(
            DailyReflectionService dailyReflectionService
    ) {
        this.dailyReflectionService = dailyReflectionService;
    }

    @GetMapping("/today")
    public ResponseEntity<ReflectionResponse> getToday() {
        return ResponseEntity.ok()
                .cacheControl(
                        CacheControl.maxAge(Duration.ofHours(1))
                                .cachePublic()
                )
                .body(dailyReflectionService.getToday());
    }

    @GetMapping("/random")
    public List<ReflectionResponse> getRandomReflections(
            @RequestParam(defaultValue = "3") int limit
    ) {
        return dailyReflectionService.getRandomReflections(limit);
    }

}