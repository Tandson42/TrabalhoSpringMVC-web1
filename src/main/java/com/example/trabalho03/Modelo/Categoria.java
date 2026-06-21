package com.example.trabalho03.Modelo;

public class Categoria {
    private String nome;
    private int id;

    public Categoria(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }
    public Categoria() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }
}
