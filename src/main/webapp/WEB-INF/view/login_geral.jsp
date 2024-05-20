<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css"/>' />
  </head>
  <body>
    <div>
      <header>
			<a href="index">Home</a> 
			<a onclick="carrinho()">Carrinho</a> 
			<a onclick="conta()">Conta</a>
		</header>
    </div>
    <form action="" method="post">
      <main>
        <h1>Login</h1>
        <div class="linha">
          <div style="width: 50%; height: 50%">
            <h3>Vendedor</h3>
            <p class="btn"><a style="color: white" href="login_vendedor">Login Vendedor</a></p>
          </div>
          <div style="margin-left: 10px; width: 50%; height: 50%">
            <h3>Cliente</h3>
            <p class="btn"><a style="color: white" href="login_cliente">Login Cliente</a></p>

            <p class="btn"><a style="color: white" href="cadastrar_cliente">Criar uma Conta</a></p>
          </div>
        </div>
      </main>
    </form>
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
