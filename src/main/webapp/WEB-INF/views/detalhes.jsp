<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="pt-BR"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title><c:out value="${item.titulo}"/> - Catálogo</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"></head>
<body class="bg-light"><main class="container py-4" style="max-width: 900px"><a href="${pageContext.request.contextPath}/catalogo?acao=listar">&larr; Voltar</a>
<div class="card mt-3"><div class="card-body"><span class="badge text-bg-primary"><c:out value="${item.tipoMidia}"/></span><h1 class="card-title mt-2"><c:out value="${item.titulo}"/></h1>
<dl class="row"><dt class="col-sm-3">Autor/Diretor</dt><dd class="col-sm-9"><c:out value="${item.autorDiretor}"/></dd><dt class="col-sm-3">Ano</dt><dd class="col-sm-9"><c:out value="${item.anoLancamento}"/></dd>
<dt class="col-sm-3">Gênero</dt><dd class="col-sm-9"><c:out value="${item.genero}"/></dd><dt class="col-sm-3">Sinopse</dt><dd class="col-sm-9"><c:out value="${item.sinopse}"/></dd></dl>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/catalogo?acao=editar&amp;id=${item.id}">Editar</a></div></div></main></body></html>
