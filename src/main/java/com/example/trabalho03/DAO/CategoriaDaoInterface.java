package com.example.trabalho03.DAO;

import com.example.trabalho03.Modelo.Usuario;
import com.example.trabalho03.Modelo.Categoria;
import java.util.List;

public interface CategoriaDaoInterface {
    public void editar(int id, String nome) throws ErroDao;
    public void adicionar(String nome) throws ErroDao;
    public void remover(int id) throws ErroDao;
    public List<Categoria> buscar() throws ErroDao;
    public Categoria buscar(int id) throws ErroDao;
    public void sair() throws ErroDao;
}
