<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <c:if test="${requestScope.mensagem != null}">
        <div class="alerta ${requestScope.tipo}">
                ${requestScope.mensagem}
        </div>
    </c:if>
    <p>Bem vindo! </p>
    <a href="vertarefas"> Ver as tarefas</a> <br>
    <a href="cadastrartarefas"> Cadastrar Tarefa</a> <br>
    <c:if test="${sessionScope.usuario.admin}"> <br>
        <a href="cadastraradmin"> Adicionar Admin</a> <br>
    </c:if>
    <a href="alterarcadastro"> Alterar Cadastro </a> <br>
    <c:if test="${sessionScope.usuario.admin}">
        <a href="vercategorias"> Ver categorias </a> <br>
    </c:if>
    <a href="logout"> Deslogar </a>
</body>
</html>
