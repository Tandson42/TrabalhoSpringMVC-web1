<%@page pageEncoding="UTF-8" contentType="text/html" %>
    <%@taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Cadastrar Tarefas</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <div class="mensagem">
                ${param.mensagem}
            </div>
            <form action="cadastrartarefas" method="post">
                <label>Título <input type="text" name="titulo" placeholder="Título"></label>
                <label>Descrição <input type="text" name="descricao" placeholder="Descrição"></label>
                <label>Data <input type="date" name="data"></label>
                <label>Categoria
                    <select name="categoria.id" required>
                        <option value="">Selecione</option>
                        <c:forEach var="c" items="${requestScope.categorias}">
                            <option value="${c.id}">${c.nome}</option>
                        </c:forEach>
                    </select>
                </label>
                <input type="submit" value="Cadastrar">
            </form>
            <a href="menu"> Voltar </a>
        </body>

        </html>