<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Ver Tarefas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>Id</th>
                <th>Título</th>
                <th>Descrição</th>
                <th>Data</th>
                <th>Categoria</th>
                <th>Deletar</th>
                <th>Editar</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="t" items="${requestScope.tarefas}">
                <tr>
                    <td>${t.id}</td>
                    <td>${t.titulo}</td>
                    <td>${t.descricao}</td>
                    <td>${t.data}</td>
                    <td>${t.categoria.nome}</td>
                    <td><a href="deletartarefa?id=${t.id}">Deletar</a></td>
                    <td><a href="editartarefa?id=${t.id}">Editar</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="menu"> Voltar </a>
</body>
</html>
