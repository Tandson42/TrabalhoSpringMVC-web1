package com.example.trabalho03.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private int id;
    private String nome,login, senha;
    private List<Tarefa> tarefas=new ArrayList<>();
    private boolean admin;

    public Usuario(){}

    public Usuario(String nome, String login, String senha, boolean admin) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.admin = admin;
    }

    public Usuario(int id, String nome, String login, String senha, boolean admin) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome.length()<2)
            throw new IllegalArgumentException("O nome tem que ter no mínimo 2 caracteres");
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return this.id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", tarefas: "+tarefas+
                '}';
    }
}
