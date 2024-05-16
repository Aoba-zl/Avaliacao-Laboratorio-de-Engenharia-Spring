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
        <a href="index.html">Home</a>
        <a href="login_cliente.html">Login Cliente</a>
      </header>
    </div>
    <form action="" method="post">
      <main>
        <h1>Login Vendedor</h1>
        <div class="linha">
          <label for="login">Login:</label>
          <input type="text" name="login" id="login" />
        </div>
        <div class="linha">
          <label for="senha">Senha:</label>
          <input type="password" name="senha" id="senha" />
        </div>
        <div>
          <input type="submit" value="Realizar Login" />
        </div>
      </main>
    </form>
  </body>
</html>
