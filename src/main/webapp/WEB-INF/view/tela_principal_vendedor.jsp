<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Livraria</title>
    <link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/style.css"/>' />
</head>
<body>
<div>
    <header>
        <a href="index">Home</a>
		<a href="logout">Logout</a>
    </header>
</div>
<main>
    <div style="padding-top: 25px; padding-bottom: 25px; padding-left: 300px; padding-right: 300px">
        <table>
            <tbody>
            <tr>
                <td style="border: none; background-color: transparent">
                    <a class="btn" style="text-decoration: none;" href="consultar_clientes">Consultar Cliente</a></td>
            </tr>
            <tr style="height: 20px"></tr>
            <tr>
                <td style="border: none; background-color: transparent">
                    <a class="btn" style="text-decoration: none;" href="lista_livros">Manter Livro</a></td>
            </tr>
            <tr style="height: 20px"></tr>
            <tr>
                <td style="border: none; background-color: transparent">
                    <a class="btn" style="text-decoration: none;" href="consultar_vendas">Consultar Vendas</a></td>
            </tr>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>