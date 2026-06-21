package com.example.trabalho03.Modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Tarefa {
    private int id;
    private String titulo,descricao;
    private LocalDate data;
    private Categoria categoria;

    public Tarefa(){}
    public Tarefa(String titulo, String descricao, Categoria c) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = c;
    }
    public Tarefa(String titulo, String descricao, LocalDate data, Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
    }
    public Tarefa(int id, String titulo, String descricao, LocalDate data, Categoria c) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.categoria = c;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data=" + data +
                ", categoria=" + categoria +
                '}';
    }
}
