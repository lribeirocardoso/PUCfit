package com.projeto.pucfit.model;

import androidx.annotation.NonNull;

// Classe Aula >> somente com o título e o script de aula.
public class Aula {
    private String nome;
    private String aulaScript;

    public Aula() {
    }

    public Aula(String name, String aulaScript) {
        this.nome = name;
        this.aulaScript = aulaScript;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }
    public String getAulaScript() {
        return aulaScript;
    }

    public void setAulaScript(String aulaScript) {
        this.aulaScript = aulaScript;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome + "\n" + this.aulaScript;
    }
}
