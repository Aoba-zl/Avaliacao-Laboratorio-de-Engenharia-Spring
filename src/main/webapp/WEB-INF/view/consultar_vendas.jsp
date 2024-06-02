<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Consultar Vendas</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css"/>' />
</head>
<body>
<div>
    <header>
        <a href="index">Home</a>
        <a href="tela_principal_vendedor">Vendedor</a>
		<a href="logout">Logout</a>
    </header>
</div>
<div>
    <main>
        <form action="consultar_vendas" method="post">
            <div>
                <h1>Consultar Vendas</h1>
            </div>
            <div class="tabela_container">
                <table>
                    <thead>
                    <th>Email do Cliente</th>
                    <th>Data do Pagamento</th>
                    <th>Preço Total</th>
                    <th>Status</th>
                    <th>Ação</th>
                    </thead>
                    <tbody>
                    <c:if test="${not empty vendas}">
                        <c:forEach var="v" items="${vendas}">
                            <tr>
                                <td><c:out value="${v.cliente.email}" /></td>
                                <td><c:out value="${v.dataPagamento}" /></td>
                                <td><fmt:formatNumber value="${v.precoTotal}" type="currency" currencyCode="BRL" /></td>
                                <c:if test="${v.finalizada == true}">
                                    <td>Finalizada</td>
                                </c:if>
                                <c:if test="${v.finalizada == false}">
                                    <td>Em preparo</td>
                                </c:if>
                                <c:if test="${v.finalizada == false}">
                                    <td><a href="${pageContext.request.contextPath}/consultar_vendas?codigo=${v.codigo}&acao=Consultar">Consultar </a> | <a href="${pageContext.request.contextPath}/consultar_vendas?codigo=${v.codigo}&acao=Finalizar">Finalizar</a></td>
                                </c:if>
                                <c:if test="${v.finalizada == true}">
                                    <td><a href="${pageContext.request.contextPath}/consultar_vendas?codigo=${v.codigo}&acao=Consultar">Consultar </a></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </c:if>

                    </tbody>
                </table>
            </div>
            <br>
            <c:if test="${not empty erro}">
                <div>
                    <b><c:out value="${erro}" /></b>
                </div>
            </c:if>
            <c:if test="${not empty saida}">
                <div>
                    <b><c:out value="${saida}" /></b>
                </div>
            </c:if>
            <br>
            <h2>Detalhes da Venda</h2>
            <br>
            <div class="tabela_container">
                <table>
                    <thead>
                    <th>Título</th>
                    <th>Quantidade</th>
                    <th>Preço</th>
                    </thead>
                    <tbody>
                    <c:if test="${not empty itens}">
                        <c:forEach var="i" items="${itens}">
                            <tr>
                                <td><c:out value="${i.livro.titulo}" /></td>
                                <td><c:out value="${i.quantidade}" /></td>
                                <td><fmt:formatNumber value="${i.total}" type="currency" currencyCode="BRL" /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
            <div align="left">
                <label for="subtotal">Subtotal:</label>
                <input type="text" name="subtotal" id="subTotal" placeholder="R$ 00,00" value="${subTotal}" disabled>
            </div>
            <div align="left">
                <label for="desconto">Desconto:</label>
                <input type="text" name="desconto" id="desconto" placeholder="R$ 00,00" value="${desconto}" disabled>
            </div>
            <div align="left">
                <label for="total">Total:</label>
                <input type="text" name="total" id="total" placeholder="R$ 00,00"
                       value="${total}" disabled>

            </div>
        </form>
    </main>
</div>
</body>
</html>
