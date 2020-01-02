ALTER TABLE sessao_votacao
ADD COLUMN votacao_encerrada boolean NOT NULL,
ADD COLUMN pauta_aprovada boolean NOT NULL,
ADD COLUMN total_votos int NOT NULL,
ADD COLUMN total_votos_favoravel int NOT NULL,
ADD COLUMN total_votos_contrario int NOT NULL;
