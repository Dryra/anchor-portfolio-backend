CREATE TABLE reflection_collection_translations (
                                                    collection_id VARCHAR(80) NOT NULL,
                                                    locale VARCHAR(35) NOT NULL,

                                                    title VARCHAR(120) NOT NULL,
                                                    subtitle VARCHAR(255),

                                                    CONSTRAINT pk_reflection_collection_translations
                                                        PRIMARY KEY (collection_id, locale),

                                                    CONSTRAINT fk_collection_translations_collection
                                                        FOREIGN KEY (collection_id)
                                                            REFERENCES reflection_collections(id)
                                                            ON UPDATE CASCADE
                                                            ON DELETE CASCADE,

                                                    CONSTRAINT chk_collection_translations_locale_not_blank
                                                        CHECK (BTRIM(locale) <> ''),

                                                    CONSTRAINT chk_collection_translations_title_not_blank
                                                        CHECK (BTRIM(title) <> '')
);


CREATE TABLE reflection_translations (
                                         reflection_id BIGINT NOT NULL,
                                         locale VARCHAR(35) NOT NULL,

                                         text TEXT NOT NULL,

                                         CONSTRAINT pk_reflection_translations
                                             PRIMARY KEY (reflection_id, locale),

                                         CONSTRAINT fk_reflection_translations_reflection
                                             FOREIGN KEY (reflection_id)
                                                 REFERENCES reflections(id)
                                                 ON DELETE CASCADE,

                                         CONSTRAINT chk_reflection_translations_locale_not_blank
                                             CHECK (BTRIM(locale) <> ''),

                                         CONSTRAINT chk_reflection_translations_text_not_blank
                                             CHECK (BTRIM(text) <> '')
);


INSERT INTO reflection_collection_translations (
    collection_id,
    locale,
    title,
    subtitle
)
SELECT
    id,
    'en',
    title,
    subtitle
FROM reflection_collections;


INSERT INTO reflection_translations (
    reflection_id,
    locale,
    text
)
SELECT
    id,
    'en',
    text
FROM reflections;