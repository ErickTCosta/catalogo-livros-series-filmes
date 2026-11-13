<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="pt-BR"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Catálogo de mídias</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"></head>
<body class="bg-light"><main class="container py-4"><div class="d-flex justify-content-between align-items-center mb-4"><h1>Catálogo de mídias</h1>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/catalogo?acao=novo">Novo cadastro</a></div>
<c:if test="${not empty sucesso}"><div class="alert alert-success"><c:out value="${sucesso}"/></div></c:if>
<c:if test="${not empty erro}"><div class="alert alert-danger"><c:out value="${erro}"/></div></c:if>
<form class="row g-2 mb-4" method="get" action="${pageContext.request.contextPath}/catalogo"><input type="hidden" name="acao" value="buscar">
<div class="col-md-10"><label class="visually-hidden" for="termo">Buscar</label><input class="form-control" id="termo" name="termo" value="<c:out value='${param.termo}'/>" placeholder="Buscar por título ou autor/diretor"></div>
<div class="col-md-2 d-grid"><button class="btn btn-outline-primary" type="submit">Buscar</button></div></form>
<c:choose><c:when test="${empty itens}"><div class="alert alert-info">Nenhum item encontrado.</div></c:when><c:otherwise>
<div class="table-responsive"><table class="table table-striped align-middle bg-white"><thead><tr><th>Título</th><th>Tipo</th><th>Autor/Diretor</th><th>Ano</th><th>Ações</th></tr></thead><tbody>
<c:forEach var="item" items="${itens}"><tr><td><c:out value="${item.titulo}"/></td><td><c:out value="${item.tipoMidia}"/></td><td><c:out value="${item.autorDiretor}"/></td><td><c:out value="${item.anoLancamento}"/></td><td class="text-nowrap">
<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/catalogo?acao=detalhes&amp;id=${item.id}">Detalhes</a>
<a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/catalogo?acao=editar&amp;id=${item.id}">Editar</a>
<a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/catalogo?acao=excluir&amp;id=${item.id}" onclick="return confirm('Excluir este item?')">Excluir</a>
</td></tr></c:forEach></tbody></table></div></c:otherwise></c:choose></main></body></html>
