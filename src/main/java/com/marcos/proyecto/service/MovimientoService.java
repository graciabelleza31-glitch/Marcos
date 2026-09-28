package com.marcos.proyecto.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.Movimiento;
import com.marcos.proyecto.modelo.Producto;

@Service
public class MovimientoService {

    private final List<Movimiento> movimientos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    @Autowired
    private ProductoService productoService;

    public MovimientoService() {
        // Datos de prueba iniciales
        movimientos.add(new Movimiento(contador.getAndIncrement(), 1L, 1L, "ENTRADA", 20, LocalDate.now(), "Compra inicial", "Arroz 1kg"));
        movimientos.add(new Movimiento(contador.getAndIncrement(), 2L, 1L, "SALIDA", 5, LocalDate.now(), "Ajuste por merma", "Aceite 1L"));
        movimientos.add(new Movimiento(contador.getAndIncrement(), 3L, 1L, "ENTRADA", 15, LocalDate.now(), "Reposición de stock", "Gaseosa 1.5L"));
    }

    public List<Movimiento> listar() {
        return movimientos;
    }

    public List<Movimiento> listarUltimos() {
        return movimientos.stream()
                .sorted((m1, m2) -> Long.compare(m2.getIdMov(), m1.getIdMov()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public long totalEntradasHoy() {
        return movimientos.stream()
                .filter(m -> "ENTRADA".equalsIgnoreCase(m.getTipoMov()) && LocalDate.now().equals(m.getFecMov()))
                .count();
    }

    public long totalSalidasHoy() {
        return movimientos.stream()
                .filter(m -> "SALIDA".equalsIgnoreCase(m.getTipoMov()) && LocalDate.now().equals(m.getFecMov()))
                .count();
    }

    // REGISTRAR MOVIMIENTO Y ALTERAR STOCK
    public void registrarMovimiento(Long idProd, String tipoMov, int cantidad, String motivo, Long idUs) {
        Producto prod = productoService.obtener(idProd);

        if ("ENTRADA".equalsIgnoreCase(tipoMov)) {
            productoService.aumentarStock(idProd, cantidad);
        } else if ("SALIDA".equalsIgnoreCase(tipoMov)) {
            productoService.descontarStock(idProd, cantidad);
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido: " + tipoMov);
        }

        Movimiento mov = new Movimiento(
                contador.getAndIncrement(),
                idProd,
                idUs != null ? idUs : 1L,
                tipoMov.toUpperCase(),
                cantidad,
                LocalDate.now(),
                motivo,
                prod.getNomProd()
        );

        movimientos.add(mov);
    }
}