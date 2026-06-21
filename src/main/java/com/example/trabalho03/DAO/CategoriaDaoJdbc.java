package com.example.trabalho03.DAO;

import com.example.trabalho03.Modelo.Usuario;
import com.example.trabalho03.Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoJdbc implements CategoriaDaoInterface{
    private Connection con;

    public CategoriaDaoJdbc() throws ErroDao {
        con = FabricaConexao.pega();
    }

    @Override
    public void editar(int id, String nome) throws ErroDao {
        String sql = "update categoria set nome=? where id=?";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, nome);
            pstm.setInt(2, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public void adicionar(String nome) throws ErroDao {
        String sql="insert into categoria(nome) values(?)";

        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1,nome);
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public void remover(int id) throws ErroDao {
        String sql = "delete from categoria where id=?";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public List<Categoria> buscar() throws ErroDao {
        List<Categoria> categorias = new ArrayList<>();
        PreparedStatement pstm = null;
        try {
            pstm = con.prepareStatement("select * from categoria");
            ResultSet rs=pstm.executeQuery();
            while (rs.next()){
                Categoria c=new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                categorias.add(c);
            }
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
        return categorias;
    }

    @Override
    public Categoria buscar(int id) throws ErroDao {
        String sql = "select * from categoria where id=?";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                return c;
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
