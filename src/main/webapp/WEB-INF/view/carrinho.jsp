<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Carrinho</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css"/>' />
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/index">Home</a>
        <a onclick="conta()">Conta</a>
    </header>
<div>
    <main>
        <form action="carrinho" method="post">
            <div>
                <h1>Carrinho</h1>
            </div>
            <input type="submit" value="Refresh">
            <div class="tabela_container">
                <table>
                    <thead>
                    <th>Título</th>
                    <th>Quantidade</th>
                    <th>Preço</th>
                    <th>Ação</th>
                    </thead>
                    <tbody>
                    <c:if test="${not empty produtosCarrinho}">
                        <c:forEach var="p" items="${produtosCarrinho}">
                            <tr>
                                <td><c:out value="${p.livro.titulo}" /></td>
                                <td><c:out value="${p.quantidade}" /></td>
                                <td><fmt:formatNumber value="${p.total}" type="currency" currencyCode="BRL" /></td>
                                <td><a href="${pageContext.request.contextPath}/carrinho?codigo=${p.id}&acao=Remover">Remover</a></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
            <c:if test="${not empty erro}">
                <div>
                    <b><c:out value="${erro}"/></b>
                </div>
            </c:if>
            <c:if test="${not empty saida}">
                <div>
                    <b><c:out value="${saida}"/></b>
                </div>
            </c:if>
            <div align="left">
                <label for="subtotal">Subtotal:</label>
                <input type="text" name="subTotal" id="subTotal" placeholder="R$ 00,00" value="${subTotal}" disabled>
            </div>
            <div align="left">
                <label for="desconto">Desconto:</label>
                <input type="text" name="desconto" id="desconto" placeholder="R$ 00,00" value="${desconto}" disabled>
            </div>
            <div align="left">
                <label for="total">Total:</label>
                <input type="text" name="total" id="total" placeholder="R$ 00,00" value="${total}" disabled>
                <div align="right">
                    <input type="submit" name="botao" id="botao" value="Finalizar Compra">
                </div>
            </div>
        </form>
    </main>
</div>
</body>
<script>
    function cookieExists(cookieName) {
        // Separe todos os cookies em um array
        const cookiesArray = document.cookie.split('; ');

        // Verifique se o cookieName está presente no array de cookies
        for (let i = 0; i < cookiesArray.length; i++) {
            const cookie = cookiesArray[i];
            const [name, value] = cookie.split('=');
            if (name === cookieName) {
                return true;
            }
        }

        // Retorna false se o cookie não for encontrado
        return false;
    }

    function conta()
    {
        if (cookieExists('login'))
        {
            window.location.href = "${pageContext.request.contextPath}/manter_cliente.jsp"
        }
        else
        {
            window.location.href = "${pageContext.request.contextPath}/login_cliente.jsp"
        }
    }
</script>
</html>
