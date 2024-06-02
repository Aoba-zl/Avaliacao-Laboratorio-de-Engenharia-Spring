<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Visualizar Livro</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css"/>' />
</head>
<body>
<div>
    <header>
		<a href="index">Home</a>
		<c:if test="${not empty login_v}">
		<a href="tela_principal_vendedor">Vendedor</a>
		<a href="logout">Logout</a>
		</c:if>
		<c:if test="${empty login_v}">
		<a onclick="carrinho()">Carrinho</a>
		<a onclick="conta()">Conta</a>
		</c:if>
	</header>
</div>
<form action="consulta_livro" method="post">
    <main>
        <input
                hidden
                type="number"
                name="codigoLivro"
                id="codigoLivro"
                value="${codigoLivro}"
        />
        <div class="linha">
            <label>Título</label>
            <input
                    disabled
                    type="text"
                    name="titulo"
                    id="titulo"
                    value="${livro.titulo}"
            />
        </div>
        <div class="linha">
            <label>Descrição</label>
            <textarea disabled name="descricao" id="descricao" cols="25" rows="4"><c:out value="${livro.descricao}"/> </textarea>
        </div>
        <div class="linha">
            <label for="genero">Gênero</label>
            <input
                    type="text"
                    disabled
                    name="genero"
                    id="genero"
                    value="${livro.genero}"
            />
        </div>
        <div class="linha">
            <label for="autor">Autor</label>
            <input
                    type="text"
                    disabled
                    name="autor"
                    id="autor"
                    value="${livro.autor}"
            />
        </div>
        <div class="linha">
            <label for="preco">Preço</label>
            <input
                    type="text"
                    disabled
                    name="preco"
                    id="preco"
                    value="<fmt:formatNumber value="${livro.preco}" type="currency"
										currencyCode="BRL" />"
            >
        </div>
        <div class="linha">
            <label for="dt_publicacao">Data de Publicação</label>
            <input disabled type="date" name="dt_publicacao" id="dt_publicacao" value="${livro.data_publicacao}"/>
        </div>
        <div class="linha">
            <label for="num_paginas">Númoro de Páginas</label>
            <input
                    type="text"
                    disabled
                    name="num_paginas"
                    id="num_paginas"
                    value="${livro.paginas}"
            />
        </div>
        <div class="linha">
            <label for="estoque">Estoque</label>
            <input
                    type="text"
                    disabled
                    name="estoque"
                    id="estoque"
                    value="${livro.estoque}"
            />
        </div>
        <div class="linha">
            <label for="qntd">Quantidade:</label>
            <input type="number" min="1" name="qntd" id="qntd" value="${qntd}" />
            <c:if test="${empty login_v}">
			<input type="submit" value="Adicionar ao Carrinho" />
			</c:if>
            
        </div>
    </main>
</form>
<div align="center">
    <c:if test="${not empty erro }">
        <h2>
            <b><c:out value="${erro }" /></b>
        </h2>
    </c:if>
</div>

<br />
<div align="center">
    <c:if test="${not empty saida }">
        <h3>
            <b><c:out value="${saida }" /></b>
        </h3>
    </c:if>
</div>
</body>
<script>
	function conta() {
		<c:if test="${not empty login_c}">
		window.location.href = "manter_cliente"
		</c:if>
		<c:if test="${empty login_c}">
		window.location.href = "login_cliente"
		</c:if>
	}
	function carrinho() {
		<c:if test="${not empty login_c}">
		window.location.href = "carrinho"
		</c:if>
		<c:if test="${empty login_c}">
		window.location.href = "login_cliente"
		</c:if>
	}
</script>
</html>