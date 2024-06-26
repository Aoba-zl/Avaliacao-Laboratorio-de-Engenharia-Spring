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

    <script type="text/javascript">
        window.onload = function() {
            // Limpa a URL removendo os parâmetros de consulta
            if (window.history.replaceState) {
                const cleanUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
                window.history.replaceState({ path: cleanUrl }, "", cleanUrl);
            }
        };
    </script>
</head>
<body>
    <header>
        <a href="index">Home</a> 
			<a onclick="conta()">Conta</a>
    </header>
<div>
    <main>
        <form action="carrinho" method="post">
            <div>
                <h1>Carrinho</h1>
            </div>
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
                                <td>
                                    <a class="mais_e_menos" href="${pageContext.request.contextPath}/carrinho?livro_codigo=${p.livro.codigo}&codigo=${p.id}&acao=Diminuir"> - </a>
                                    <c:out value="${p.quantidade}" />
                                    <a class="mais_e_menos" href="${pageContext.request.contextPath}/carrinho?livro_codigo=${p.livro.codigo}&codigo=${p.id}&acao=Aumentar"> + </a>
                                </td>
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
	function conta() {
		<c:if test="${not empty login_c}">
		window.location.href = "manter_cliente"
		</c:if>
		<c:if test="${empty login_c}">
		window.location.href = "login_cliente"
		</c:if>
	}
</script>
</html>
