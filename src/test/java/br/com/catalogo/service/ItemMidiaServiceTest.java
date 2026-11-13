package br.com.catalogo.service;

import br.com.catalogo.dao.ItemMidiaDAO;
import br.com.catalogo.model.ItemMidia;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testa as validações de negócio do serviço de itens de mídia.
 */
class ItemMidiaServiceTest {
    private final ItemMidiaService service = new ItemMidiaService(new ItemMidiaDAO() {
        @Override public void inserir(ItemMidia item) throws SQLException { }
        @Override public boolean atualizar(ItemMidia item) throws SQLException { return true; }
    });

    @Test
    void deveAceitarItemValido() {
        assertDoesNotThrow(() -> service.inserir(itemValido()));
    }

    @Test
    void deveRejeitarTituloVazio() {
        ItemMidia item = itemValido();
        item.setTitulo(" ");
        assertThrows(IllegalArgumentException.class, () -> service.inserir(item));
    }

    @Test
    void deveRejeitarTipoInvalido() {
        ItemMidia item = itemValido();
        item.setTipoMidia("Jogo");
        assertThrows(IllegalArgumentException.class, () -> service.inserir(item));
    }

    @Test
    void deveRejeitarTextoAcimaDoLimite() {
        ItemMidia item = itemValido();
        item.setSinopse("a".repeat(2001));
        assertThrows(IllegalArgumentException.class, () -> service.inserir(item));
    }

    @Test
    void deveRejeitarAnoFuturo() {
        ItemMidia item = itemValido();
        item.setAnoLancamento(java.time.Year.now().getValue() + 1);
        assertThrows(IllegalArgumentException.class, () -> service.inserir(item));
    }

    private ItemMidia itemValido() {
        return new ItemMidia(null, "Duna", "Frank Herbert", 1965,
                "Ficção científica", "Uma sinopse", "Livro");
    }
}
