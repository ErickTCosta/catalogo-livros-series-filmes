package br.com.catalogo.service;

import br.com.catalogo.dao.ItemMidiaDAO;
import br.com.catalogo.model.ItemMidia;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;
import java.util.Set;

/**
 * Aplica regras de negócio antes de acessar o DAO.
 */
public class ItemMidiaService {
    private static final Set<String> TIPOS = Set.of("Livro", "Filme", "Série");
    private final ItemMidiaDAO dao;

    /** Cria o serviço usando um DAO padrão. */
    public ItemMidiaService() { this(new ItemMidiaDAO()); }

    /** Cria o serviço com um DAO fornecido, útil para testes. */
    public ItemMidiaService(ItemMidiaDAO dao) { this.dao = dao; }

    /** Valida e insere um item. */
    public void inserir(ItemMidia item) throws SQLException {
        validar(item);
        dao.inserir(item);
    }

    /** Valida e atualiza um item. */
    public boolean atualizar(ItemMidia item) throws SQLException {
        validar(item);
        if (item.getId() == null) {
            throw new IllegalArgumentException("O identificador do item é obrigatório para edição.");
        }
        return dao.atualizar(item);
    }

    /** Exclui um item. */
    public boolean excluir(Integer id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("Identificador inválido.");
        return dao.excluir(id);
    }

    /** Busca um item pelo identificador. */
    public ItemMidia buscarPorId(Integer id) throws SQLException { return dao.buscarPorId(id); }
    /** Lista todos os itens. */
    public List<ItemMidia> listarTodos() throws SQLException { return dao.listarTodos(); }
    /** Busca por título ou autor/diretor. */
    public List<ItemMidia> buscarPorTituloOuAutor(String termo) throws SQLException {
        return termo == null || termo.isBlank() ? dao.listarTodos() : dao.buscarPorTituloOuAutor(termo);
    }

    /** Valida campos obrigatórios, limites de tamanho e ano. */
    public void validar(ItemMidia item) {
        if (item == null) throw new IllegalArgumentException("Item de mídia inválido.");
        validarObrigatorio(item.getTitulo(), "O título é obrigatório.");
        validarTamanho(item.getTitulo(), 200, "O título deve ter no máximo 200 caracteres.");
        validarObrigatorio(item.getTipoMidia(), "O tipo de mídia é obrigatório.");
        if (!TIPOS.contains(item.getTipoMidia())) throw new IllegalArgumentException("O tipo de mídia deve ser Livro, Filme ou Série.");
        validarTamanho(item.getAutorDiretor(), 200, "Autor/diretor deve ter no máximo 200 caracteres.");
        validarTamanho(item.getGenero(), 100, "Gênero deve ter no máximo 100 caracteres.");
        validarTamanho(item.getSinopse(), 2000, "A sinopse deve ter no máximo 2000 caracteres.");
        if (item.getAnoLancamento() != null
                && (item.getAnoLancamento() < 1 || item.getAnoLancamento() > Year.now().getValue())) {
            throw new IllegalArgumentException("O ano de lançamento deve ser um ano válido.");
        }
    }

    private void validarObrigatorio(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(mensagem);
    }
    private void validarTamanho(String valor, int limite, String mensagem) {
        if (valor != null && valor.length() > limite) throw new IllegalArgumentException(mensagem);
    }
}
