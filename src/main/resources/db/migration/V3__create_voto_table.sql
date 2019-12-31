CREATE TABLE voto (
  id SERIAL PRIMARY KEY,
  pauta_id bigint NOT NULL,
  sessao_id bigint NOT NULL,
  voto_opcao varchar(3) NOT NULL,
  associado_cpf varchar(11) NOT NULL
);
