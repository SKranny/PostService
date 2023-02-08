CREATE TABLE IF NOT EXISTS post (
    id                          BIGSERIAL PRIMARY KEY,
    time                        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    author_id                   BIGINT NOT NULL,
    title                       VARCHAR(255) NOT NULL,
    post_text                   TEXT NOT NULL,
    is_blocked                  BOOLEAN NOT NULL DEFAULT false,
    is_delete                   BOOLEAN NOT NULL DEFAULT false,
    with_friends                BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS tag (
    id                          BIGSERIAL PRIMARY KEY,
    tag                         VARCHAR(255) NOT NULL
);

CREATE TABLE jt_post_tag (
    post_id                     BIGINT REFERENCES post(id),
    tag_id                      BIGINT REFERENCES tag(id),
    PRIMARY KEY (post_id, tag_id)
);