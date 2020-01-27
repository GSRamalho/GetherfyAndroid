package com.guilherme.presentation.sala.model;


public class Sala {
    private int id;
    private String nome;
    private int capacidade;
    private boolean possuiArcon;
    private boolean possuiMultimidia;
    private double area;
    private boolean possuiAcessiblidade;
    private String localidade;
    private boolean ativa;

    public Sala(
            int id, String nome, int capacidade,
            boolean possuiArcon, boolean possuiMultimidia,
            double area, boolean possuiAcessiblidade, String localidade, boolean ativa) {

        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.possuiArcon = possuiArcon;
        this.possuiMultimidia = possuiMultimidia;
        this.area = area;
        this.localidade = localidade;
        this.ativa = ativa;
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

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}
