package com.example.trabalho03.DAO;

import com.example.trabalho03.Modelo.Usuario;
import com.example.trabalho03.Modelo.Tarefa;

import java.util.List;

public interface TarefaDaoInterface {
    public void salvar(Tarefa t,Usuario u) throws ErroDao;
    public List<Tarefa> buscar(Usuario u) throws ErroDao;
    public void deletar(Tarefa t) throws ErroDao;
    public void deletar(int id) throws ErroDao;
    public void editar(Tarefa t) throws ErroDao;
    public Tarefa buscar(int id, Usuario u) throws ErroDao;
    public void sair() throws ErroDao;
}
