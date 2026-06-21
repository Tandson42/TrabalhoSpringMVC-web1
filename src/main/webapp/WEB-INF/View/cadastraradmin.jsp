<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Cadastrar ADMIN</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:if test="${requestScope.mensagem != null}">
    <div class="alerta">${requestScope.mensagem}</div>
</c:if>

<c:if test="${sessionScope.usuario.admin}">
    <p> Adicionar Admin </p>
    <form action="cadastraradmin" method="post">
        <label>Nome: <input type="text" name="nome" placeholder="Nome"></label>
        <label>Login: <input type="text" name="login" placeholder="Login"></label>
        <label>Senha: <input type="password" name="senha" placeholder="Senha"></label>
        <input type="submit" value="Cadastrar">
    </form>
    <a href="menu"> Voltar </a>
</c:if>

<c:if test="${!sessionScope.usuario.admin}">
    <p> Você não é ADMIN </p>
    <a href="vertarefas"> Voltar </a>
</c:if>

</body>
</html>
