ALTER TABLE post ADD COLUMN my_like BOOLEAN NOT NULL DEFAULT false;

CREATE TABLE IF NOT EXISTS comment_likes (
    id                          BIGSERIAL PRIMARY KEY,
    user_id                     BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS post_likes (
    id                          BIGSERIAL PRIMARY KEY,
    user_id                     BIGINT NOT NULL
);

CREATE TABLE comment2like (
  comment_like_id  BIGINT REFERENCES comment_likes(id),
  comment_id       BIGINT,
  PRIMARY KEY (comment_like_id, comment_id),
  FOREIGN KEY (comment_id) REFERENCES comments(id)
);

CREATE TABLE post2like (
    post_like_id                      BIGINT REFERENCES post_likes(id),
    post_id                           BIGINT REFERENCES post(id),
    PRIMARY KEY (post_like_id, post_id)
);

