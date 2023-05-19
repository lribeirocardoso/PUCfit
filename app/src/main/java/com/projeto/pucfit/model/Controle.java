package com.projeto.pucfit.model;

import java.util.Date;

// Classe Controle >> controle de alunos realizado pelo professor/instrutor.
public class Controle {

    private String nome;
    private String sobrenome;
    private String idPessoa;
    private Date createdTime;

    public Controle() {}

    public Controle(String nome, String sobrenome, String idPessoa, Date createdTime) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idPessoa = idPessoa;
        this.createdTime = createdTime;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getIdPessoa() {
        return idPessoa;
    }

    public Date getCreatedTime(){
        return createdTime;
    }
}
