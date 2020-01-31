package com.guilherme.getherfy.usuario.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private int idOrganizacao;
    private String senha;

    public Usuario(int id, String nome, String email, int idOrganizacao, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idOrganizacao = idOrganizacao;
        this.senha = senha;
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
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(int idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}