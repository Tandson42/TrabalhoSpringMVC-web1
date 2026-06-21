package com.example.trabalho03.DAO;

import com.example.trabalho03.Modelo.Usuario;
import com.example.trabalho03.Modelo.Tarefa;
import com.example.trabalho03.Modelo.Categoria;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarefaDaoJdbc implements TarefaDaoInterface {
    private Connection con;

    public TarefaDaoJdbc() throws ErroDao {
        con = FabricaConexao.pega();
    }

    @Override
    public void salvar(Tarefa t, Usuario u) throws ErroDao {
        PreparedStatement pstm = null;
        try {
            pstm = con.prepareStatement(
                    "insert into tarefas(titulo,descricao,data,id_usuario,id_categoria) values (?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, t.getTitulo());
            pstm.setString(2, t.getDescricao());
            if (t.getData() != null)
                pstm.setDate(3, new Date(t.getData().toEpochDay()));
            else
                pstm.setDate(3, null);
            pstm.setInt(4, u.getId());
            pstm.setInt(5, t.getCategoria().getId());
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new ErroDao(e);
        }

    }

    @Override
    public List<Tarefa> buscar(Usuario u) throws ErroDao {
        List<Tarefa> tarefas = new ArrayList<>();
        PreparedStatement pstm = null;
        try {
            pstm = con.prepareStatement(
                    "select t.*, c.nome as categoria_nome from tarefas t, categoria c where t.id_categoria = c.id and t.id_usuario=?");
            pstm.setInt(1, u.getId());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));
                if (rs.getDate("data") != null)
                    t.setData(LocalDate.ofEpochDay(rs.getDate("data").getTime()));
                Categoria c = new Categoria();
                c.setId(rs.getInt("id_categoria"));
                c.setNome(rs.getString("categoria_nome"));
                t.setCategoria(c);
                tarefas.add(t);
            }
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
        return tarefas;
    }

    @Override
    public void deletar(Tarefa t) throws ErroDao {
        deletar(t.getId());
    }

    @Override
    public void deletar(int id) throws ErroDao {
        String sql = "delete from tarefas where id=?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public void editar(Tarefa t) throws ErroDao {
        String sql = "update tarefas set titulo=?, descricao=?, data=?, id_categoria=? where id=?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, t.getTitulo());
            pstm.setString(2, t.getDescricao());
            if (t.getData() != null)
                pstm.setDate(3, new Date(t.getData().toEpochDay()));
            else
                pstm.setDate(3, null);
            pstm.setInt(4, t.getCategoria().getId());
            pstm.setInt(5, t.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public Tarefa buscar(int id, Usuario u) throws ErroDao {
        String sql = "select t.*, c.nome as categoria_nome from tarefas t, categoria c where t.id_categoria = c.id and t.id=? and t.id_usuario=?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.setInt(2, u.getId());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));
                if (rs.getDate("data") != null)
                    t.setData(LocalDate.ofEpochDay(rs.getDate("data").getTime()));
                Categoria c = new Categoria();
                c.setId(rs.getInt("id_categoria"));
                c.setNome(rs.getString("categoria_nome"));
                t.setCategoria(c);
                return t;
            }
            return null;
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public void sair() throws ErroDao {
        try {
            con.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }
}
