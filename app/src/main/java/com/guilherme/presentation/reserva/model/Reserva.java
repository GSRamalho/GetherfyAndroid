package com.guilherme.presentation.reserva.model;

import java.util.Date;

public class Reserva {
    private int id;
    private String sala;
    private int organizador;
    private String dataHoraInicio;
    private String dataHoraFim;
    private boolean ativa;

    public Reserva(int id, String sala, int organizador, String dataHoraInicio, String dataHoraFim, boolean ativa) {
        this.id = id;
        this.sala = sala;
        this.organizador = organizador;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.ativa = ativa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getOrganizador() {
        return organizador;
    }

    public void setOrganizador(int organizador) {
        this.organizador = organizador;
    }

    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(String dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
