package com.guilherme.getherfy.sala.model;


public class Sala {
    private int id;
    private int idOrganizacao;
    private String nome;
    private int capacidade;
    private boolean possuiArcon;
    private boolean possuiMultimidia;
    private double area;
    private boolean possuiAcessiblidade;
    private String localizacao;
    private boolean ativa;

    public Sala() {
        this.id = id;
        this.idOrganizacao = idOrganizacao;
        this.nome = nome;
        this.capacidade = capacidade;
        this.possuiArcon = possuiArcon;
        this.possuiMultimidia = possuiMultimidia;
        this.area = area;
        this.possuiAcessiblidade = possuiAcessiblidade;
        this.localizacao = localizacao;
        this.ativa = ativa;
    }

    public int getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(int idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public boolean isPossuiArcon() {
        return possuiArcon;
    }

    public boolean isPossuiMultimidia() {
        return possuiMultimidia;
    }

    public boolean isPossuiAcessiblidade() {
        return possuiAcessiblidade;
    }


    public boolean possuiAcessiblidade() {
        return possuiAcessiblidade;
    }

    public void setPossuiAcessiblidade(boolean possuiAcessiblidade) {
        this.possuiAcessiblidade = possuiAcessiblidade;
    }

    public boolean possuiArcon() {
        return possuiArcon;
    }

    public void setPossuiArcon(boolean possuiArcon) {
        this.possuiArcon = possuiArcon;
    }

    public boolean possuiMultimidia() {
        return possuiMultimidia;
    }

    public void setPossuiMultimidia(boolean possuiMultimidia) {
        this.possuiMultimidia = possuiMultimidia;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}
