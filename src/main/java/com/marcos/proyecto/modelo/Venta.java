package com.marcos.proyecto.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private Long idVenta;
    private Long idUs;
    private String nomCliente;
    private String numDocumentoCliente; 
    private LocalDate fecVenta;
    private BigDecimal totalVenta;
    private String metodoPago; 
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
        this.fecVenta = LocalDate.now();
        this.totalVenta = BigDecimal.ZERO;
    }

    public void calcularTotal() {
        this.totalVenta = detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getIdVenta() { 
        return idVenta; 
    }
    public void setIdVenta(Long idVenta) { 
        this.idVenta = idVenta; 
    }

    public Long getIdUs() { 
        return idUs; 
    }
    public void setIdUs(Long idUs) { 
        this.idUs = idUs; 
    }

    public String getNomCliente() { 
        return nomCliente; 
    }
    public void setNomCliente(String nomCliente) {
         this.nomCliente = nomCliente; 
        }

    public String getNumDocumentoCliente() { 
        return numDocumentoCliente; 
    }
    public void setNumDocumentoCliente(String numDocumentoCliente) { 
        this.numDocumentoCliente = numDocumentoCliente; 
    }

    public LocalDate getFecVenta() { 
        return fecVenta; 
    }
    public void setFecVenta(LocalDate fecVenta) { 
        this.fecVenta = fecVenta; 
    }

    public BigDecimal getTotalVenta() { 
        return totalVenta; 
    }
    public void setTotalVenta(BigDecimal totalVenta) { 
        this.totalVenta = totalVenta; 
    }

    public String getMetodoPago() { 
        return metodoPago; 
    }
    public void setMetodoPago(String metodoPago) { 
        this.metodoPago = metodoPago; 
    }

    public List<DetalleVenta> getDetalles() { 
        return detalles; 
    }
    public void setDetalles(List<DetalleVenta> detalles) { 
        this.detalles = detalles; 
    }
}