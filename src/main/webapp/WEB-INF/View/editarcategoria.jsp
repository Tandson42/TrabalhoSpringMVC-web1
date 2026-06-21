<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Editar Categoria</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:if test="${sessionScope.usuario.admin}">
    <c:if test="${requestScope.mensagem != null}">
        <div class="alerta ${requestScope.tipo}">
                ${requestScope.mensagem}
        </div>
    </c:if>

    <p> Editar categoria</p>
    <form action="editarcategoria" method="post">
        <input type="hidden" name="id" value="${requestScope.categoria.id}">
        <label>Nome: <input type="text" name="nome" value="${requestScope.categoria.nome}" placeholder="Nome"></label>
        <input type="submit" value="Salvar">
    </form>

    <a href="vercategorias"> Voltar </a>
</c:if>
<c:if test="${!sessionScope.usuario.admin}">
    <p>Você não tem permissão para acessar esta página.</p>
    <a href="menu"> Voltar </a>
</c:if>
</body>
</html>
