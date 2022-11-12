CREATE TABLE IF NOT EXISTS post_service.post(
    id bigint NOT NULL,
    "time" date NOT NULL,
    author_id bigint NOT NULL,
    title text COLLATE pg_catalog."default" NOT NULL,
    post_text text COLLATE pg_catalog."default" NOT NULL,
    is_blocked boolean NOT NULL,
    CONSTRAINT post_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post_service.tag
(
    id bigint NOT NULL,
    tag text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tag_pkey PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS post_service.post2tag(
    id bigint NOT NULL,
    post_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    CONSTRAINT post2tag_pkey PRIMARY KEY (id)
);



