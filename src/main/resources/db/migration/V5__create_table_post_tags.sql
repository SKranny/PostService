create TABLE IF NOT EXISTS post_tags(
  post_id           BIGINT,
  tag_id            BIGINT,
  PRIMARY KEY (post_id, tag_id),
  CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES post(id),
  CONSTRAINT fk_tag FOREIGN KEY(tag_id) REFERENCES tag(id)

);











