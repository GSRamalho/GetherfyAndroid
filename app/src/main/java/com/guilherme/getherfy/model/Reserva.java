package com.guilherme.getherfy.model;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {
    private int id;
    private int sala;
    private int organizador;
    private String dataHoraInicio;
    private String dataHoraFim;
    private boolean ativo;
    private String descricao;
    private String nomeOrganizador;
    private String nomeSala;



    public Reserva() {
        this.id = id;
        this.sala = sala;
        this.organizador = organizador;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.ativo = ativo;
        this.descricao = descricao;
        this.nomeSala = nomeSala;
    }

    public String getNomeOrganizador() {
        return nomeOrganizador;
    }

    public void setNomeOrganizador(String nomeOrganizador) {
        this.nomeOrganizador = nomeOrganizador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativa) {
        this.ativo = ativo;
    }
    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

}
