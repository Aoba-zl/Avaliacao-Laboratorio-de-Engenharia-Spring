<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login Vendedor</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css"/>' />
  </head>
  <body>
    <div>
      <header>
        <a href="index">Home</a>
        <a href="login_cliente">Login Cliente</a>
      </header>
    </div>
    <form action="login_vendedor" method="post">
      <main>
        <h1>Login Vendedor</h1>
        <div class="linha">
          <label for="login">Login:</label>
          <input type="text" name="login" id="login" value='<c:out value="${vendedor.login}"/>' />
        </div>
        <div class="linha">
          <label for="senha">Senha:</label>
          <input type="password" name="senha" id="senha" value='<c:out value="${vendedor.senha}"/>' />
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
        <div>
          <input type="submit" name="botao" value="Realizar Login" />
        </div>
      </main>
    </form>
  </body>
</html>
