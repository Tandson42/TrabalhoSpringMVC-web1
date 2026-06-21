package com.example.trabalho03.DAO;

import com.example.trabalho03.Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDaoJdbc implements UsuarioDaoInterface{
    private Connection con;
    public UsuarioDaoJdbc() throws ErroDao {
        con=FabricaConexao.pega();
    }

    @Override
    public void salvar(Usuario u) throws ErroDao {
        if (!existeUsuario(u.getLogin())) {
        PreparedStatement pstm= null;
        try {
            pstm = con.prepareStatement(
                    "insert into usuarios(nome,login,senha,admin) values (?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1,u.getNome());
            pstm.setString(2,u.getLogin());
            pstm.setString(3,u.getSenha());
            pstm.setString(4,Boolean.toString(u.isAdmin()));
            pstm.executeUpdate();
            ResultSet rs=pstm.getGeneratedKeys();
            if(rs.next()){
                u.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new ErroDao(e);
        }

    }
        else {
            throw new ErroDao("Usuário com login já existe!");
        }
    }

    @Override
    public Usuario buscar(String login, String senha) throws ErroDao {
        try {
            PreparedStatement pstm=con.prepareStatement(
                    "select * from usuarios where login=? and senha=?");
            pstm.setString(1,login);
            pstm.setString(2,senha);
            ResultSet rs=pstm.executeQuery();
            if(rs.next()){
                Usuario u=new Usuario();
                u.setId(rs.getInt("id"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setNome(rs.getString("nome"));
                u.setAdmin(rs.getBoolean("admin"));

                return u;
            }else
                return null;
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    public boolean existeUsuario(String login) throws ErroDao {
        String sql="select count(*) from usuarios where login=?";

        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1,login);
            try (ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    int count=rs.getInt(1);
                    if(count>0){
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
        return false;
    }

    @Override
    public void deletar(Usuario u) throws ErroDao {

    }

    @Override
    public void deletar(int id) throws ErroDao {

    }

    @Override
    public void editar(Usuario u) throws ErroDao {

    }

    @Override
    public void editar(int id, String nome, String login, String senha) throws ErroDao {
        String sql="update usuarios set nome=?, login=?, senha=? where id=?";

        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, nome);
            pstm.setString(2, login);
            pstm.setString(3, senha);
            pstm.setInt(4, id);
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public void editar(String login, String senha, int id) throws ErroDao {
        String sql="update usuarios set login=?, senha=? where id=? ";

        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1,login);
            pstm.setString(2,senha);
            pstm.setInt(3,id);
            pstm.executeUpdate();

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
