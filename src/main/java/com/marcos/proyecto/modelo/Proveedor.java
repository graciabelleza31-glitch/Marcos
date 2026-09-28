package com.marcos.proyecto.modelo;

public class Proveedor {
    private Long idProv;
    private String nomProv;
    private String rucProv;
    private String telProv;

    public Proveedor() { }

    public Proveedor(Long idProv, String nomProv, String rucProv, String telProv) {
        this.idProv = idProv;
        this.nomProv = nomProv;
        this.rucProv = rucProv;
        this.telProv = telProv;
    }

    public Long getIdProv() { return idProv; }
    public void setIdProv(Long idProv) { this.idProv = idProv; }

    public String getNomProv() { return nomProv; }
    public void setNomProv(String nomProv) { this.nomProv = nomProv; }

    public String getRucProv() { return rucProv; }
    public void setRucProv(String rucProv) { this.rucProv = rucProv; }

    public String getTelProv() { return telProv; }
    public void setTelProv(String telProv) { this.telProv = telProv; }
}
