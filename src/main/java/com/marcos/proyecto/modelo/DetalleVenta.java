package com.marcos.proyecto.modelo;

import java.math.BigDecimal;

public class DetalleVenta {
    private Long idDetalle;
    private Long idVenta;
    private Long idProd;
    private String nomProd;
    private Integer cantDetalle;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetalleVenta() {
        this.cantDetalle = 1;
        this.precioUnitario = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
    }

    public DetalleVenta(Long idProd, String nomProd, Integer cantDetalle, BigDecimal precioUnitario) {
        this.idProd = idProd;
        this.nomProd = nomProd;
        this.cantDetalle = cantDetalle;
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public void calcularSubtotal() {
        if (this.cantDetalle != null && this.precioUnitario != null) {
            this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantDetalle));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }

    public Long getIdDetalle() { 
        return idDetalle; 
    }
    public void setIdDetalle(Long idDetalle) {
         this.idDetalle = idDetalle; 
        }

    public Long getIdVenta() { 
        return idVenta; 
    }
    public void setIdVenta(Long idVenta) { 
        this.idVenta = idVenta; 
    }

    public Long getIdProd() { 
        return idProd; 
    }
    public void setIdProd(Long idProd) { 
        this.idProd = idProd; 
    }

    public String getNomProd() { 
        return nomProd; 
    }
    public void setNomProd(String nomProd) { 
        this.nomProd = nomProd; 
    }

    public Integer getCantDetalle() { 
        return cantDetalle; 
    }

    public void setCantDetalle(Integer cantDetalle) { 
        this.cantDetalle = cantDetalle; 
        calcularSubtotal();
    }

    public BigDecimal getPrecioUnitario() { 
        return precioUnitario; 
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) { 
        this.precioUnitario = precioUnitario; 
        calcularSubtotal();
    }

    public BigDecimal getSubtotal() { 
        return subtotal; 
    }
    public void setSubtotal(BigDecimal subtotal) { 
        this.subtotal = subtotal; 
    }
}