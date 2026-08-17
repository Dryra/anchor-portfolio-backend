-- Fictional demonstration content for the portfolio edition.
INSERT INTO reflection_collections
    (id, title, subtitle, symbol, premium, seasonal, active, sort_order,
     available_from, available_until)
VALUES
    ('focus', 'Focus', 'Prompts for intentional work', 'scope', FALSE, FALSE, TRUE, 1, NULL, NULL),
    ('gratitude_demo', 'Gratitude Demo', 'Sample content for local evaluation', 'heart', FALSE, FALSE, TRUE, 2, NULL, NULL),
    ('seasonal_demo', 'Seasonal Demo', 'An example of date-bound availability', 'calendar', TRUE, TRUE, TRUE, 3,
     DATE '2026-01-01', DATE '2027-12-31');
