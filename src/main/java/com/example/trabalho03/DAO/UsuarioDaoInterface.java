package com.example.trabalho03.DAO;

import com.example.trabalho03.Modelo.Usuario;

public interface UsuarioDaoInterface {
    public void salvar(Usuario u) throws ErroDao;
    public Usuario buscar(String login,String senha) throws ErroDao;
    public void deletar(Usuario u) throws ErroDao;
    public void deletar(int id) throws ErroDao;
    public void editar(Usuario u) throws ErroDao;
    public void sair() throws ErroDao;

    //metodo de editar
    public void editar(String login, String senha, int id) throws ErroDao;
    public void editar(int id, String nome, String login, String senha) throws ErroDao;
}
