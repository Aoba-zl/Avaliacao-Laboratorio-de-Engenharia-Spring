<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Visualizar Livro</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css?version=51"/>' />
</head>
<body>
<div>
    <header>
    	<a href="index">Home</a>
        <a href="tela_principal_vendedor">Vendedor</a>
		<a href="logout">Logout</a>
    </header>
</div>
<form action="manter_livro" method="post">
    <main>
        <div class="linha">
            <label>Código</label>
            <input
                    type="number"
                    name="codigoLivro"
                    id="codigoLivro"
                    value="${codigoLivro}"
            />
        </div>
        <div class="linha">
            <label>Título</label>
            <input
                    type="text"
                    name="titulo"
                    id="titulo"
                    value="${livro.titulo}"
            />
        </div>
        <div class="linha">
            <label>Descrição</label>
            <textarea name="descricao" id="descricao" cols="25" rows="4"><c:out value="${livro.descricao}"/> </textarea>
        </div>
        <div class="linha">
            <label for="genero">Gênero</label>
            <input
                    type="text"
                    name="genero"
                    id="genero"
                    value="${livro.genero}"
            />
        </div>
        <div class="linha">
            <label for="autor">Autor</label>
            <input
                    type="text"
                    name="autor"
                    id="autor"
                    value="${livro.autor}"
            />
        </div>
        <div class="linha">
            <label for="preco">Preço</label>
            <input
                    type="text"
                    name="preco"
                    id="preco"
                    value="R$${livro.preco}"
            >
        </div>
        <div class="linha">
            <label for="dt_publicacao">Data de Publicação</label>
            <input type="date" name="dt_publicacao" id="dt_publicacao" value="${livro.data_publicacao}"/>
        </div>
        <div class="linha">
            <label for="num_paginas">Número de Páginas</label>
            <input
                    type="text"
                    name="num_paginas"
                    id="num_paginas"
                    value="${livro.paginas}"
            />
        </div>
        <div class="linha">
            <label for="estoque">Estoque</label>
            <input
                    type="text"
                    name="estoque"
                    id="estoque"
                    value="${livro.estoque}"
            />
        </div>
        <div class="linha">
            <input type="submit" value="Salvar" />
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
</html>