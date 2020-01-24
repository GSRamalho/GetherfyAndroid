package com.guilherme.presentation.sala;

import android.graphics.drawable.Drawable;

public class Sala {
    private int id;
    private String nome;
    private int capacidade;
    private boolean arcon;
    private boolean multimidia;
    private double area;
    private String localidade;
    private boolean ativa;

    public Sala(
            int id, String nome, int capacidade,
            boolean arcon, boolean multimidia,
            double area, String localidade, boolean ativa) {

        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.arcon = arcon;
        this.multimidia = multimidia;
        this.area = area;
        this.localidade = localidade;
        this.ativa = ativa;
    }

    public boolean isArcon() {
        return arcon;
    }

    public void setArcon(boolean arcon) {
        this.arcon = arcon;
    }

    public boolean isMultimidia() {
        return multimidia;
    }

    public void setMultimidia(boolean multimidia) {
        this.multimidia = multimidia;
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
