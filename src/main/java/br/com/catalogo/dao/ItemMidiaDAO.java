package br.com.catalogo.dao;

import br.com.catalogo.model.ItemMidia;
import br.com.catalogo.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Executa operações de persistência de itens de mídia.
 */
public class ItemMidiaDAO {
    private static final String COLUNAS = "id, titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia";

    /** Insere um item e atualiza seu identificador gerado. */
    public void inserir(ItemMidia item) throws SQLException {
        String sql = "INSERT INTO itens_midia (titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preencher(statement, item);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    item.setId(keys.getInt(1));
                }
            }
        }
    }

    /** Atualiza um item existente. */
    public boolean atualizar(ItemMidia item) throws SQLException {
        String sql = "UPDATE itens_midia SET titulo=?, autor_diretor=?, ano_lancamento=?, genero=?, sinopse=?, tipo_midia=? WHERE id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            preencher(statement, item);
            statement.setInt(7, item.getId());
            return statement.executeUpdate() == 1;
        }
    }

    /** Exclui um item pelo identificador. */
    public boolean excluir(Integer id) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM itens_midia WHERE id=?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    /** Busca um item pelo identificador. */
    public ItemMidia buscarPorId(Integer id) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT " + COLUNAS + " FROM itens_midia WHERE id=?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapear(result) : null;
            }
        }
    }

    /** Lista todos os itens ordenados por título. */
    public List<ItemMidia> listarTodos() throws SQLException {
        return executarLista("SELECT " + COLUNAS + " FROM itens_midia ORDER BY titulo", null);
    }

    /** Busca itens por trecho do título ou autor/diretor. */
    public List<ItemMidia> buscarPorTituloOuAutor(String termo) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM itens_midia WHERE titulo LIKE ? OR autor_diretor LIKE ? ORDER BY titulo";
        String filtro = "%" + termo.trim() + "%";
        return executarLista(sql, filtro);
    }

    private List<ItemMidia> executarLista(String sql, String filtro) throws SQLException {
        List<ItemMidia> itens = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (filtro != null) {
                statement.setString(1, filtro);
                statement.setString(2, filtro);
            }
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    itens.add(mapear(result));
                }
            }
        }
        return itens;
    }

    private void preencher(PreparedStatement statement, ItemMidia item) throws SQLException {
        statement.setString(1, item.getTitulo());
        statement.setString(2, item.getAutorDiretor());
        if (item.getAnoLancamento() == null) {
            statement.setNull(3, Types.INTEGER);
        } else {
            statement.setInt(3, item.getAnoLancamento());
        }
        statement.setString(4, item.getGenero());
        statement.setString(5, item.getSinopse());
        statement.setString(6, item.getTipoMidia());
    }

    private ItemMidia mapear(ResultSet result) throws SQLException {
        return new ItemMidia(result.getInt("id"), result.getString("titulo"),
                result.getString("autor_diretor"), (Integer) result.getObject("ano_lancamento"),
                result.getString("genero"), result.getString("sinopse"), result.getString("tipo_midia"));
    }
}
