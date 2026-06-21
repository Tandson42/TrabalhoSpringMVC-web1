<%@page pageEncoding="UTF-8" contentType="text/html" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gerenciador de Tarefas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="mensagem">
    ${param.mensagem}
</div>

<c:if test="${requestScope.mensagem != null}">
    <div class="alerta ${requestScope.tipo}">
            ${requestScope.mensagem}
    </div>
</c:if>

<form method="post" action="cadastrarusuario">
    <label>Nome: <input type="text" name="nome" placeholder="Nome"></label>
    <label>Login: <input type="text" name="login" placeholder="Login"></label>
    <label>Senha: <input type="password" name="senha" placeholder="Senha"></label>
    <button type="submit">Cadastrar</button>
</form>

<form action="logar" method="post">
    <label>Login: <input type="text" name="login" placeholder="Login"></label>
    <label>Senha: <input type="password" name="senha" placeholder="Senha"></label>
    <input type="submit" value="Logar">
</form>
</body>
</html>