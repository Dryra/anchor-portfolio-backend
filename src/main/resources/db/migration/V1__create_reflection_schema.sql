CREATE TABLE reflection_collections (
    id VARCHAR(80) PRIMARY KEY,
    title VARCHAR(120) NOT NULL,
    subtitle VARCHAR(255),
    symbol VARCHAR(100) NOT NULL,

    premium BOOLEAN NOT NULL DEFAULT FALSE,
    seasonal BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    sort_order INTEGER NOT NULL DEFAULT 0,

    available_from DATE,
    available_until DATE,

    CONSTRAINT chk_reflection_collections_id_not_blank
        CHECK (BTRIM(id) <> ''),

    CONSTRAINT chk_reflection_collections_title_not_blank
        CHECK (BTRIM(title) <> ''),

    CONSTRAINT chk_reflection_collections_symbol_not_blank
        CHECK (BTRIM(symbol) <> ''),

    CONSTRAINT chk_reflection_collections_sort_order
        CHECK (sort_order >= 0),

    CONSTRAINT chk_reflection_collections_availability
        CHECK (
            available_from IS NULL
                OR available_until IS NULL
                OR available_from <= available_until
            )
);

CREATE TABLE reflections (
     id BIGSERIAL PRIMARY KEY,

     text TEXT NOT NULL,
     collection_id VARCHAR(80) NOT NULL,

     premium BOOLEAN NOT NULL DEFAULT FALSE,
     daily_eligible BOOLEAN NOT NULL DEFAULT FALSE,
     active BOOLEAN NOT NULL DEFAULT TRUE,

     sort_order INTEGER NOT NULL DEFAULT 0,

     CONSTRAINT fk_reflections_collection
         FOREIGN KEY (collection_id)
             REFERENCES reflection_collections(id)
             ON UPDATE CASCADE
             ON DELETE RESTRICT,

     CONSTRAINT chk_reflections_text_not_blank
         CHECK (BTRIM(text) <> ''),

     CONSTRAINT chk_reflections_sort_order
         CHECK (sort_order >= 0),

     CONSTRAINT uq_reflections_collection_sort_order
         UNIQUE (collection_id, sort_order)
);

CREATE INDEX idx_collections_active_sort
    ON reflection_collections(active, sort_order);

CREATE INDEX idx_collections_availability
    ON reflection_collections(
      active,
      available_from,
      available_until,
      sort_order
    );

CREATE INDEX idx_reflections_collection_active_sort
    ON reflections(
       collection_id,
       active,
       sort_order
    );

CREATE INDEX idx_reflections_daily_active
    ON reflections(
       daily_eligible,
       active
    );