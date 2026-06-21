<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Ver Categorias</title>
</head>
<body>
<c:if test="${sessionScope.usuario.admin}">

    <c:if test="${requestScope.mensagem != null}">
        <div class="alerta ${requestScope.tipo}">
                ${requestScope.mensagem}
        </div>
    </c:if>
    <table>
        <thead>
            <tr>
                <th>Id</th>
                <th>Nome</th>
                <th>Deletar</th>
                <th>Editar</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${requestScope.categorias}">
            <tr>
                <td>${c.id}</td>
            <td>${c.nome}</td>
            <td><a href="deletarcategoria?id=${c.id}">Deletar</a></td>
            <td><a href="editarcategoria?id=${c.id}">Editar</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p> Adicionar categoria</p>
    <form action="adicionarcategoria" method="post">
        <label>Nome: <input type="text" name="nome" placeholder="Nome"></label>
        <input type="submit" value="Adicionar">
    </form>

    <a href="menu"> Voltar </a>
</c:if>
<c:if test="${!sessionScope.usuario.admin}">
    <p>Você não tem permissão para acessar esta página.</p>
    <a href="menu"> Voltar </a>
</c:if>
</body>
</html>
