package com.marcos.proyecto.modelo;

import java.time.LocalDate;

public class Movimiento {
    private Long idMov;
    private Long idProd;
    private Long idUs;
    private String tipoMov;     
    private Integer cantMov;
    private LocalDate fecMov;
    private String motivoMov;   

    private String nomProd;
    private String nomUs;


    public Movimiento() {
    }

    public Movimiento(Long idMov, Long idProd, Long idUs, String tipoMov, 
                      Integer cantMov, LocalDate fecMov, String motivoMov, String nomProd) {
        this.idMov = idMov;
        this.idProd = idProd;
        this.idUs = idUs;
        this.tipoMov = tipoMov;
        this.cantMov = cantMov;
        this.fecMov = fecMov;
        this.motivoMov = motivoMov;
        this.nomProd = nomProd;
    }


    public Movimiento(Long idMov, Long idProd, Long idUs, String tipoMov, 
                      Integer cantMov, LocalDate fecMov, String motivoMov, 
                      String nomProd, String nomUs) {
        this.idMov = idMov;
        this.idProd = idProd;
        this.idUs = idUs;
        this.tipoMov = tipoMov;
        this.cantMov = cantMov;
        this.fecMov = fecMov;
        this.motivoMov = motivoMov;
        this.nomProd = nomProd;
        this.nomUs = nomUs;
    }

    public Long getIdMov() {
        return idMov;
    }

    public void setIdMov(Long idMov) {
        this.idMov = idMov;
    }

    public Long getIdProd() {
        return idProd;
    }

    public void setIdProd(Long idProd) {
        this.idProd = idProd;
    }

    public Long getIdUs() {
        return idUs;
    }

    public void setIdUs(Long idUs) {
        this.idUs = idUs;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public Integer getCantMov() {
        return cantMov;
    }

    public void setCantMov(Integer cantMov) {
        this.cantMov = cantMov;
    }

    public LocalDate getFecMov() {
        return fecMov;
    }

    public void setFecMov(LocalDate fecMov) {
        this.fecMov = fecMov;
    }

    public String getMotivoMov() {
        return motivoMov;
    }

    public void setMotivoMov(String motivoMov) {
        this.motivoMov = motivoMov;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getNomUs() {
        return nomUs;
    }

    public void setNomUs(String nomUs) {
        this.nomUs = nomUs;
    }

    
}