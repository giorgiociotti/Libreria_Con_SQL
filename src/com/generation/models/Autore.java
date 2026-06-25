package com.generation.models;

public class Autore extends Entity{

    private String nome;
    private String cognome;
    
    public Autore(Long id, String nome, String cognome) {
        super(id);
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "Autore [nome=" + nome + ", cognome=" + cognome + ", id =" + getId() + "]";
    }

    

    
}
