CREATE TABLE sessao_votacao (
  id SERIAL PRIMARY KEY,
  pauta_id bigint NOT NULL,
  end_date_time timestamp NOT NULL,
  start_date_time timestamp NOT NULL
);
