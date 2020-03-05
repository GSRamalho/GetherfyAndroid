package com.guilherme.getherfy.model;

import java.util.Date;

public class Organizacao {

    private int id;
    private int idOrganizacaoPai;
    private String tipoOrganizacao;
    private String dominio;
    private int ativo;
    private Date dataCriacao;
    private Date dataAlteracao;
    private String nome;

    public Organizacao() {
        this.id = id;
        this.idOrganizacaoPai = idOrganizacaoPai;
        this.tipoOrganizacao = tipoOrganizacao;
        this.dominio = dominio;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrganizacaoPai() {
        return idOrganizacaoPai;
    }

    public void setIdOrganizacaoPai(int idOrganizacaoPai) {
        this.idOrganizacaoPai = idOrganizacaoPai;
    }

    public String getTipoOrganizacao() {
        return tipoOrganizacao;
    }

    public void setTipoOrganizacao(String tipoOrganizacao) {
        this.tipoOrganizacao = tipoOrganizacao;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
