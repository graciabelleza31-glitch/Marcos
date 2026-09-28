package com.marcos.proyecto.modelo;

import java.math.BigDecimal;

public class Producto {
    private Long idProd;
    private Long idCat;
    private Long idProv;
    private String codProd;
    private String nomProd;
    private String descProd;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stockProd;
    private Integer stockMinimo;
    private String unidadMedida;     
    private String estado;      
    private String imagen;  

    // Auxiliares para mostrar en la lista
    private String nomCat;
    private String nomProv;

    // Constructor vacío
    public Producto() {
    }

    // Constructor completo
    public Producto(Long idProd, Long idCat, Long idProv, String codProd, String nomProd,
                    String descProd, BigDecimal precioCompra, BigDecimal precioVenta,
                    Integer stockProd, Integer stockMinimo, String unidadMedida,
                    String estado, String imagen, String nomCat, String nomProv) {
        this.idProd = idProd;
        this.idCat = idCat;
        this.idProv = idProv;
        this.codProd = codProd;
        this.nomProd = nomProd;
        this.descProd = descProd;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockProd = stockProd;
        this.stockMinimo = stockMinimo;
        this.unidadMedida = unidadMedida;
        this.estado = estado;
        this.imagen = imagen;
        this.nomCat = nomCat;
        this.nomProv = nomProv;
    }

    // Getters y Setters
    public Long getIdProd() { return idProd; }
    public void setIdProd(Long idProd) { this.idProd = idProd; }

    public Long getIdCat() { return idCat; }
    public void setIdCat(Long idCat) { this.idCat = idCat; }

    public Long getIdProv() { return idProv; }
    public void setIdProv(Long idProv) { this.idProv = idProv; }

    public String getCodProd() { return codProd; }
    public void setCodProd(String codProd) { this.codProd = codProd; }

    public String getNomProd() { return nomProd; }
    public void setNomProd(String nomProd) { this.nomProd = nomProd; }

    public String getDescProd() { return descProd; }
    public void setDescProd(String descProd) { this.descProd = descProd; }

    public BigDecimal getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public Integer getStockProd() { return stockProd; }
    public void setStockProd(Integer stockProd) { this.stockProd = stockProd; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getNomCat() { return nomCat; }
    public void setNomCat(String nomCat) { this.nomCat = nomCat; }

    public String getNomProv() { return nomProv; }
    public void setNomProv(String nomProv) { this.nomProv = nomProv; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    // Método auxiliar para saber si está en stock bajo
    public boolean isStockBajo() {
        return stockProd != null && stockMinimo != null && stockProd <= stockMinimo;
    }
}
