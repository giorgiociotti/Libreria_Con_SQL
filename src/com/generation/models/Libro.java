package com.generation.models;

public class Libro extends Entity {

    private String titolo;
    private int pagine;
    private Long idAutore; // Chiave Esterna (FK)

    public Libro(Long id, String titolo, int pagine, Long idAutore) {
        super(id);
        this.titolo = titolo;
        this.pagine = pagine;
        this.idAutore = idAutore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getPagine() {
        return pagine;
    }

    public void setPagine(int pagine) {
        this.pagine = pagine;
    }

    public Long getIdAutore() {
        return idAutore;
    }

    public void setIdAutore(Long idAutore) {
        this.idAutore = idAutore;
    }

    @Override
    public String toString() {
        return "Libro [id=" + getId() + ", titolo=" + titolo + ", pagine=" + pagine + ", idAutore=" + idAutore + "]";
    }
}