package net.dryra.anchorbackend.localization;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class LocaleService {

    private static final String DEFAULT_LOCALE = "en";

    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    public List<String> getCandidateLocales() {
        Locale locale = getCurrentLocale();

        Set<String> candidates = new LinkedHashSet<>();

        String fullTag = normalize(locale.toLanguageTag());

        if (!fullTag.isBlank() && !fullTag.equals("und")) {
            candidates.add(fullTag);
        }

        String language = normalize(locale.getLanguage());

        if (!language.isBlank()) {
            candidates.add(language);
        }

        candidates.add(DEFAULT_LOCALE);

        return new ArrayList<>(candidates);
    }

    private String normalize(String locale) {
        return locale
                .trim()
                .replace('_', '-')
                .toLowerCase(Locale.ROOT);
    }
}