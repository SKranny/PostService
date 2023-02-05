CREATE TABLE IF NOT EXISTS post(
    id                          BIGSERIAL NOT NULL,
    "time"                      DATE NOT NULL,
    author_id                   BIGINT NOT NULL,
    title                       TEXT COLLATE pg_catalog."default" NOT NULL,
    post_text                   TEXT COLLATE pg_catalog."default" NOT NULL,
    is_blocked                  BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS tag
(
    id                         BIGSERIAL NOT NULL,
    tag                        TEXT COLLATE pg_catalog."default" NOT NULL
);


CREATE TABLE IF NOT EXISTS post_tags(

 post_id INT,
  tag_id INT,
  PRIMARY KEY (post_id, tag_id),
  CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES post(id),
  CONSTRAINT fk_tag FOREIGN KEY(tag_id) REFERENCES tag(id)

);





