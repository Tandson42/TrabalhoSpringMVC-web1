<%@page pageEncoding="UTF-8" contentType="text/html" %>
    <%@taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Editar Tarefa</title>
        </head>

        <body>
            <c:if test="${requestScope.mensagem != null}">
                <div class="alerta ${requestScope.tipo}">
                    ${requestScope.mensagem}
                </div>
            </c:if>
            <form action="editartarefa" method="post">
                <input type="hidden" name="id" value="${requestScope.tarefa.id}">
                <label>Título <input type="text" name="titulo" value="${requestScope.tarefa.titulo}"
                        placeholder="Título"></label>
                <label>Descrição <input type="text" name="descricao" value="${requestScope.tarefa.descricao}"
                        placeholder="Descrição"></label>
                <label>Data <input type="date" name="data" value="${requestScope.tarefa.data}"></label>
                <label>Categoria
                    <select name="categoria" required>
                        <option value="">Selecione</option>
                        <c:forEach var="c" items="${requestScope.categorias}">
                            <option value="${c.id}" ${c.id==requestScope.tarefa.categoria.id ? 'selected' : '' }>
                                ${c.nome}</option>
                        </c:forEach>
                    </select>
                </label>
                <input type="submit" value="Salvar">
            </form>
            <a href="vertarefas"> Voltar </a>
        </body>

        </html>