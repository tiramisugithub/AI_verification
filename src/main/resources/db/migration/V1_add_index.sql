CREATE INDEX idx_store_covering
    ON p_stores (region_id, category_id, user_id, status, name, description, id, phone, address);

CREATE INDEX idx_review_store
    ON p_reviews (store_id, score, is_deleted);