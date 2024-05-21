<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Manter Cliente</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css"/>' />
</head>
<body>
<div>
    <header>
        <a href="index">Home</a> 
        <c:if test="${not empty login_c}">
		<a href="consultar_compras">Compras</a>
		</c:if>
    </header>
</div>
<main>
    <form action="manter_cliente" method="post">
        <h1>Manter Cliente</h1>
        <div class="linha">
            <label for="email">E-mail:</label>
            <input disabled="disabled" type="text" value='<c:out value="${cliente.email}"/>' />
            <input hidden type="text" name="email" id="email" value='<c:out value="${cliente.email}"/>' />
        </div>
        <div class="linha">
            <label for="senha">Senha:</label>
            <input type="text" name="senha" id="senha" value='<c:out value="${cliente.senha}"/>' />
        </div>
        <div class="linha">
            <label for="confirmsenha">Confirmar Senha:</label>
            <input type="text" name="confirmsenha" id="confirmsenha" />
        </div>
        <div class="linha">
            <label for="nome">Nome:</label>
            <input type="text" name="nome" id="nome" value='<c:out value="${cliente.nome}"/>' />
        </div>
        <div class="linha">
            <h3>Endereço</h3>
        </div>
        <div class="linha">
            <label for="log">Logradouro:</label>
            <input type="text" name="log" id="log" value='<c:out value="${cliente.endereco_logradouro}"/>' />
            <label for="numero">Número:</label>
            <input type="number" name="numero" id="numero" value='<c:out value="${cliente.endereco_numero}"/>' />
        </div>
        <c:if test="${not empty saida}">
            <div class="linha">
                <h3>Saída: <c:out value="${saida}"/></h3>
            </div>
        </c:if>
        <c:if test="${not empty erro}">
            <div class="linha">
                <h3>Erro: <c:out value="${erro}"/></h3>
            </div>
        </c:if>
        <div class="linha">
            <input class="esticado" type="submit" name="botao" value="Alterar Dados" />
            <c:if test="${not empty login_c}">
            	<input class="esticado" type="submit" name="botao" value="Sair" />
			</c:if>
			<c:if test="${not empty login_v}">
				<input class="esticado" type="submit" name="botao" value="Voltar" />
			</c:if>
        </div>
    </form>
</main>
</body>
</html>