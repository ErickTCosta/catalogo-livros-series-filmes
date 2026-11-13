package br.com.catalogo.model;

/**
 * Representa um item do catálogo de livros, filmes ou séries.
 */
public class ItemMidia {
    private Integer id;
    private String titulo;
    private String autorDiretor;
    private Integer anoLancamento;
    private String genero;
    private String sinopse;
    private String tipoMidia;

    /** Cria um item vazio. */
    public ItemMidia() {
    }

    /** Cria um item com todos os atributos. */
    public ItemMidia(Integer id, String titulo, String autorDiretor, Integer anoLancamento,
                     String genero, String sinopse, String tipoMidia) {
        this.id = id;
        this.titulo = titulo;
        this.autorDiretor = autorDiretor;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.sinopse = sinopse;
        this.tipoMidia = tipoMidia;
    }

    /** Retorna o identificador. */
    public Integer getId() { return id; }
    /** Define o identificador. */
    public void setId(Integer id) { this.id = id; }
    /** Retorna o título. */
    public String getTitulo() { return titulo; }
    /** Define o título. */
    public void setTitulo(String titulo) { this.titulo = titulo; }
    /** Retorna o autor ou diretor. */
    public String getAutorDiretor() { return autorDiretor; }
    /** Define o autor ou diretor. */
    public void setAutorDiretor(String autorDiretor) { this.autorDiretor = autorDiretor; }
    /** Retorna o ano de lançamento. */
    public Integer getAnoLancamento() { return anoLancamento; }
    /** Define o ano de lançamento. */
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }
    /** Retorna o gênero. */
    public String getGenero() { return genero; }
    /** Define o gênero. */
    public void setGenero(String genero) { this.genero = genero; }
    /** Retorna a sinopse. */
    public String getSinopse() { return sinopse; }
    /** Define a sinopse. */
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    /** Retorna o tipo de mídia. */
    public String getTipoMidia() { return tipoMidia; }
    /** Define o tipo de mídia. */
    public void setTipoMidia(String tipoMidia) { this.tipoMidia = tipoMidia; }
}
