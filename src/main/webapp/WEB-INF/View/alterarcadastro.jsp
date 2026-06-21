<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Alterar cadastro</title>
</head>
<body>
    <c:if test="${requestScope.mensagem != null}">
        <div class="alerta ${requestScope.tipo}">
                ${requestScope.mensagem}
        </div>
    </c:if>
    <p> Alterar Cadastro </p>
    <form action="alterarcadastro" method="post">
        <label>Nome: <input type="text" name="nome" value="${sessionScope.usuario.nome}" placeholder="Nome"></label>
        <label>Login: <input type="text" name="login" value="${sessionScope.usuario.login}" placeholder="Login"></label>
        <label>Senha: <input type="password" name="senha" placeholder="Senha"></label>
        <input type="submit" value="Salvar">
    </form>
    <a href="menu"> Voltar </a>
</body>
</html>
