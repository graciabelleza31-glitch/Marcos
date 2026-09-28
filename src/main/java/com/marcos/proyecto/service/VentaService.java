package com.marcos.proyecto.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.DetalleVenta;
import com.marcos.proyecto.modelo.Movimiento;
import com.marcos.proyecto.modelo.Producto;
import com.marcos.proyecto.modelo.Venta;

@Service
public class VentaService {

    private final List<Venta> ventas = new ArrayList<>();
    private final AtomicLong contadorVenta = new AtomicLong(1);
    private final AtomicLong contadorDetalle = new AtomicLong(1);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MovimientoService movimientoService;

    public List<Venta> listar() {
        return ventas;
    }

    public Venta obtener(Long id) {
        return ventas.stream()
                .filter(v -> v.getIdVenta().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Venta procesarVenta(Venta venta) {
        // 1. Validar que tenga items
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto a la venta.");
        }

        // 2. Asignar ID y fecha
        venta.setIdVenta(contadorVenta.getAndIncrement());
        venta.setFecVenta(LocalDate.now());

        // 3. Descontar stock y registrar salidas de movimientos
        for (DetalleVenta d : venta.getDetalles()) {
            d.setIdDetalle(contadorDetalle.getAndIncrement());
            d.setIdVenta(venta.getIdVenta());

            Producto p = productoService.obtener(d.getIdProd());
            d.setNomProd(p.getNomProd());
            d.setPrecioUnitario(p.getPrecioVenta());
            d.calcularSubtotal();

            // Descuenta del stock en memoria
            productoService.descontarStock(p.getIdProd(), d.getCantDetalle());
        }

        venta.calcularTotal();
        ventas.add(venta);
        return venta;
    }
}