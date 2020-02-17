package com.guilherme.getherfy.reserva.model;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {
    private int id;
    private int sala;
    private int organizador;
    private long dataHoraInicio;
    private long dataHoraFim;
    private boolean ativa;

    public Reserva() {
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

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public int getOrganizador() {
        return organizador;
    }

    public void setOrganizador(int organizador) {
        this.organizador = organizador;
    }

    public long getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(long dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public long getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(long dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
