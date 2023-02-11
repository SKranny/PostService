ALTER TABLE post ADD COLUMN publish_time TIMESTAMP WITHOUT TIME ZONE;

CREATE TABLE IF NOT EXISTS comments(
    id                          BIGSERIAL PRIMARY KEY,
    post_id                     BIGINT NOT NULL,
    time                        DATE NOT NULL,
    edit_time                   DATE,
    author_id                   BIGINT NOT NULL,
    text                        TEXT NOT NULL,
    is_blocked                  BOOLEAN NOT NULL DEFAULT FALSE,
    is_delete                   BOOLEAN NOT NULL DEFAULT FALSE,
    like_amount                 BIGINT NOT NULL DEFAULT 0,
    my_like                     BOOLEAN NOT NULL DEFAULT FALSE,
    imagepath                   TEXT,
    FOREIGN KEY (post_id) REFERENCES post(id)
);

ALTER TABLE post ADD COLUMN type TEXT;

