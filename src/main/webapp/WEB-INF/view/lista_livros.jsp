<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Livraria</title>
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
<main>
    <form action="lista_livros" method="post"/>

    <div class="linha">
        <input type="text" id="nome" name="nome" placeholder="Nome do Livro" />
        <input type="submit" value="Pesquisar" />
        <a class="btn" style="text-decoration: none; width: 115px" href="${pageContext.request.contextPath}/manter_livro">Novo</a></td>
    </div>
    <div>
        <table>
            <thead>
            <th>Título</th>
            <th>Gênero</th>
            <th>Autor</th>
            <th>Preço</th>
            <th>Estoque</th>
            <th>Ação</th>
            </thead>
            <tbody>
            <c:if test="${not empty livros}">
                <c:forEach var="l" items="${livros}">
                    <tr>
                        <td><c:out value="${l.titulo}" /></td>
                        <td><c:out value="${l.genero}" /></td>
                        <td><c:out value="${l.autor}" /></td>
                        <td><fmt:formatNumber value="${l.preco}" type="currency" currencyCode="BRL" /></td>
                        <td><c:out value="${l.estoque}" /></td>
                        <td><a href="${pageContext.request.contextPath}/manter_livro?codigo=${l.codigo}">Editar</a></td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${not empty erro}">
                <div>
                    <b><c:out value="${erro}"/></b>
                </div>
            </c:if>
            </tbody>
        </table>
    </div>
</main>
</form>
</body>
</html>