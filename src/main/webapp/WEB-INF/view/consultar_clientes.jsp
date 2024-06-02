<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Consultar Clientes</title>
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
            <form action="consultar_clientes" method="post">
                <h4>Buscar Cliente</h4>
                <div class="linha">
                    <select name="tipo_busca" id="tipo_busca">
                        <option value="e">E-mail</option>
                        <option value="n">Nome</option>
                        <option value="l">Endereço Logradouro</option>
                    </select>
                    <input type="text" name="valor_busca" id="valor_busca">
                    <input type="submit" name="botao" value="Buscar">
                </div>
                <br>
                <br>
                <h4>Clientes</h4>
                <div class="tabela_clientes">
                    <table>
                        <thead>
                            <th>E-mail</th>
                            <th>Nome</th>
                            <th>Endereço</th>
                            <th>ação</th>
                        </thead>
                        <tbody>
                            <c:if test="${not empty clientes }">
                            <c:forEach items="${clientes }" var="c">
                                <tr>
	                                <td><c:out value="${c.email }"/></td>
	                                <td><c:out value="${c.nome }"/></td>
	                                <td><c:out value="${c.endereco_logradouro }"/></td>
	                                <td>
	                                    <a href="consultar_clientes?acao=ALTERAR&email=${c.email }">Alterar Dados</a>
	                                </td>
	                            </tr>
                            </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </form>
        </main>
    </div>
</body>
</html>