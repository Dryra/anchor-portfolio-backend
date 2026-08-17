package net.dryra.anchorbackend.reflection;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dryra.anchorbackend.common.ResourceNotFoundException;
import net.dryra.anchorbackend.localization.LocaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DailyReflectionService {

    private final ReflectionRepository reflectionRepository;
    private final LocaleService localeService;

    public DailyReflectionService(
            ReflectionRepository reflectionRepository,
            LocaleService localeService
    ) {
        this.reflectionRepository = reflectionRepository;
        this.localeService = localeService;
    }

    public List<ReflectionResponse> getRandomReflections(
            int limit
    ) {
        int safeLimit = Math.max(
                1,
                Math.min(limit, 20)
        );

        List<Long> reflectionIds =
                reflectionRepository
                        .findRandomActiveFreeReflectionIds(
                                safeLimit
                        );

        if (reflectionIds.isEmpty()) {
            return List.of();
        }

        List<ReflectionEntity> reflections =
                reflectionRepository.findAllByIdIn(
                        reflectionIds
                );

        Map<Long, Integer> orderById =
                new HashMap<>();

        for (
                int index = 0;
                index < reflectionIds.size();
                index++
        ) {
            orderById.put(
                    reflectionIds.get(index),
                    index
            );
        }

        List<String> candidateLocales =
                localeService.getCandidateLocales();

        return reflections.stream()
                .sorted(
                        Comparator.comparingInt(
                                reflection ->
                                        orderById.get(
                                                reflection.getId()
                                        )
                        )
                )
                .map(reflection ->
                        ReflectionResponse.from(
                                reflection,
                                candidateLocales
                        )
                )
                .toList();
    }

    public ReflectionResponse getToday() {
        List<ReflectionEntity> reflections =
                reflectionRepository
                        .findAllByDailyEligibleTrueAndActiveTrueOrderByIdAsc();

        if (reflections.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No daily reflections are currently available."
            );
        }

        LocalDate date =
                LocalDate.now(ZoneOffset.UTC);

        int index = Math.floorMod(
                date.toEpochDay(),
                reflections.size()
        );

        List<String> candidateLocales =
                localeService.getCandidateLocales();

        return ReflectionResponse.from(
                reflections.get(index),
                candidateLocales
        );
    }
}