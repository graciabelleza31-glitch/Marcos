package com.marcos.proyecto.modelo;

public class Categoria {
    private Long idCat;
    private String nomCat;
    private String descCat;

    public Categoria() { }

    public Categoria(Long idCat, String nomCat, String descCat) {
        this.idCat = idCat;
        this.nomCat = nomCat;
        this.descCat = descCat;
    }

    public Long getIdCat() { return idCat; }
    public void setIdCat(Long idCat) { this.idCat = idCat; }

    public String getNomCat() { return nomCat; }
    public void setNomCat(String nomCat) { this.nomCat = nomCat; }

    public String getDescCat() { return descCat; }
    public void setDescCat(String descCat) { this.descCat = descCat; }
}
