package br.com.catalogo.controller;

import br.com.catalogo.model.ItemMidia;
import br.com.catalogo.service.ItemMidiaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controlador único do catálogo, responsável por encaminhar as ações HTTP.
 */
@WebServlet("/catalogo")
public class ItemMidiaServlet extends HttpServlet {
    private final ItemMidiaService service = new ItemMidiaService();

    /** Processa requisições GET de listagem, busca, formulário e detalhes. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = valorAcao(request);
        try {
            switch (acao) {
                case "novo" -> abrirFormulario(request, response, new ItemMidia());
                case "editar" -> abrirEdicao(request, response);
                case "detalhes" -> abrirDetalhes(request, response);
                case "buscar", "listar" -> listar(request, response);
                case "excluir" -> excluir(request, response);
                default -> listar(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            tratarErro(request, response, "Não foi possível concluir a operação.", e);
        }
    }

    /** Processa o salvamento de itens enviados pelo formulário. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            ItemMidia item = lerItem(request);
            if (item.getId() == null) service.inserir(item);
            else service.atualizar(item);
            request.getSession().setAttribute("sucesso", "Item salvo com sucesso.");
            response.sendRedirect(request.getContextPath() + "/catalogo?acao=listar");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("item", lerItemSemFalhar(request));
            request.getRequestDispatcher("/WEB-INF/views/formulario.jsp").forward(request, response);
        } catch (SQLException e) {
            tratarErro(request, response, "Não foi possível salvar o item.", e);
        }
    }

    private String valorAcao(HttpServletRequest request) {
        String acao = request.getParameter("acao");
        return acao == null || acao.isBlank() ? "listar" : acao;
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String termo = request.getParameter("termo");
        request.setAttribute("itens", service.buscarPorTituloOuAutor(termo));
        encaminharLista(request, response);
    }

    private void abrirFormulario(HttpServletRequest request, HttpServletResponse response, ItemMidia item)
            throws ServletException, IOException {
        request.setAttribute("item", item);
        request.getRequestDispatcher("/WEB-INF/views/formulario.jsp").forward(request, response);
    }

    private void abrirEdicao(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        ItemMidia item = service.buscarPorId(Integer.valueOf(request.getParameter("id")));
        if (item == null) {
            request.getSession().setAttribute("erro", "Item não encontrado.");
            response.sendRedirect(request.getContextPath() + "/catalogo?acao=listar");
        } else {
            abrirFormulario(request, response, item);
        }
    }

    private void abrirDetalhes(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        ItemMidia item = service.buscarPorId(Integer.valueOf(request.getParameter("id")));
        if (item == null) {
            request.getSession().setAttribute("erro", "Item não encontrado.");
            response.sendRedirect(request.getContextPath() + "/catalogo?acao=listar");
            return;
        }
        request.setAttribute("item", item);
        request.getRequestDispatcher("/WEB-INF/views/detalhes.jsp").forward(request, response);
    }

    private void excluir(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        service.excluir(Integer.valueOf(request.getParameter("id")));
        request.getSession().setAttribute("sucesso", "Item excluído com sucesso.");
        response.sendRedirect(request.getContextPath() + "/catalogo?acao=listar");
    }

    private ItemMidia lerItem(HttpServletRequest request) {
        ItemMidia item = new ItemMidia();
        String id = request.getParameter("id");
        if (id != null && !id.isBlank()) item.setId(Integer.valueOf(id));
        item.setTitulo(request.getParameter("titulo"));
        item.setAutorDiretor(request.getParameter("autorDiretor"));
        String ano = request.getParameter("anoLancamento");
        if (ano != null && !ano.isBlank()) item.setAnoLancamento(Integer.valueOf(ano));
        item.setGenero(request.getParameter("genero"));
        item.setSinopse(request.getParameter("sinopse"));
        item.setTipoMidia(request.getParameter("tipoMidia"));
        return item;
    }

    private ItemMidia lerItemSemFalhar(HttpServletRequest request) {
        try { return lerItem(request); }
        catch (NumberFormatException e) { ItemMidia item = new ItemMidia(); item.setTitulo(request.getParameter("titulo")); return item; }
    }

    private void encaminharLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object sucesso = request.getSession().getAttribute("sucesso");
        Object erro = request.getSession().getAttribute("erro");
        if (sucesso != null) { request.setAttribute("sucesso", sucesso); request.getSession().removeAttribute("sucesso"); }
        if (erro != null) { request.setAttribute("erro", erro); request.getSession().removeAttribute("erro"); }
        request.getRequestDispatcher("/WEB-INF/views/lista.jsp").forward(request, response);
    }

    private void tratarErro(HttpServletRequest request, HttpServletResponse response, String mensagem, Exception e)
            throws ServletException, IOException {
        getServletContext().log(mensagem, e);
        request.setAttribute("erro", mensagem);
        encaminharLista(request, response);
    }
}
