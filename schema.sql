CREATE TABLE IF NOT EXISTS itens_midia (
  id SERIAL PRIMARY KEY,
  titulo VARCHAR(200) NOT NULL,
  autor_diretor VARCHAR(200),
  ano_lancamento SMALLINT,
  genero VARCHAR(100),
  sinopse VARCHAR(2000),
  tipo_midia VARCHAR(20) NOT NULL,
  INDEX idx_titulo (titulo),
  INDEX idx_autor (autor_diretor),
  CONSTRAINT chk_tipo_midia CHECK (tipo_midia IN ('Livro', 'Filme', 'Série'))
);
