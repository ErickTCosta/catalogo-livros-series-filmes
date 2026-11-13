<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="pt-BR"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Formulário - Catálogo</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"></head>
<body class="bg-light"><main class="container py-4" style="max-width: 800px"><h1 class="mb-4"><c:choose><c:when test="${empty item.id}">Novo item</c:when><c:otherwise>Editar item</c:otherwise></c:choose></h1>
<c:if test="${not empty erro}"><div class="alert alert-danger"><c:out value="${erro}"/></div></c:if>
<form method="post" action="${pageContext.request.contextPath}/catalogo" class="row g-3"><input type="hidden" name="id" value="<c:out value='${item.id}'/>">
<div class="col-12"><label class="form-label" for="titulo">Título *</label><input class="form-control" id="titulo" name="titulo" required maxlength="200" value="<c:out value='${item.titulo}'/>"></div>
<div class="col-md-6"><label class="form-label" for="tipoMidia">Tipo *</label><select class="form-select" id="tipoMidia" name="tipoMidia" required><option value="">Selecione</option>
<c:forEach var="tipo" items="${['Livro','Filme','Série']}"><option value="<c:out value='${tipo}'/>" <c:if test="${item.tipoMidia eq tipo}">selected</c:if>><c:out value="${tipo}"/></option></c:forEach></select></div>
<div class="col-md-6"><label class="form-label" for="autorDiretor">Autor/Diretor</label><input class="form-control" id="autorDiretor" name="autorDiretor" maxlength="200" value="<c:out value='${item.autorDiretor}'/>"></div>
<div class="col-md-4"><label class="form-label" for="anoLancamento">Ano</label><input class="form-control" type="number" id="anoLancamento" name="anoLancamento" min="1" max="2100" value="<c:out value='${item.anoLancamento}'/>"></div>
<div class="col-md-8"><label class="form-label" for="genero">Gênero</label><input class="form-control" id="genero" name="genero" maxlength="100" value="<c:out value='${item.genero}'/>"></div>
<div class="col-12"><label class="form-label" for="sinopse">Sinopse</label><textarea class="form-control" id="sinopse" name="sinopse" maxlength="2000" rows="5"><c:out value="${item.sinopse}"/></textarea></div>
<div class="col-12"><button class="btn btn-primary" type="submit">Salvar</button> <a class="btn btn-secondary" href="${pageContext.request.contextPath}/catalogo?acao=listar">Cancelar</a></div></form></main></body></html>
