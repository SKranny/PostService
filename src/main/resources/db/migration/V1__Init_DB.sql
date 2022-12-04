CREATE TABLE IF NOT EXISTS post_service.post(
    id                          BIGSERIAL NOT NULL,
    "time"                      DATE NOT NULL,
    author_id                   BIGINT NOT NULL,
    title                       TEXT COLLATE pg_catalog."default" NOT NULL,
    post_text                   TEXT COLLATE pg_catalog."default" NOT NULL,
    is_blocked                  BOOLEAN NOT NULL,
    CONSTRAINT post_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post_service.tag
(
    id                         BIGSERIAL NOT NULL,
    tag                        TEXT COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tag_pkey PRIMARY KEY (id)
);




