package com.marcos.proyecto.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.Producto;

@Service 
public class ProductoService {
    //Actua como base de datos 
    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    // Datos de prueba
    public ProductoService() {
    productos.add(new Producto(contador.getAndIncrement(), 1L, 1L,
            "P001", "Arroz 1kg", "Arroz extra",
            new BigDecimal("4.50"), new BigDecimal("6.00"),
            50, 10, "Unidad", "Activo", "arroz.jpg",
            "Abarrotes", "Distribuidora Lima SAC"));

    productos.add(new Producto(contador.getAndIncrement(), 1L, 1L,
            "P002", "Aceite 1L", "Aceite vegetal",
            new BigDecimal("7.00"), new BigDecimal("9.50"),
            8, 10, "Unidad", "Activo", "aceite.jpg",
            "Abarrotes", "Distribuidora Lima SAC"));

    productos.add(new Producto(contador.getAndIncrement(), 2L, 2L,
            "P003", "Gaseosa 1.5L", "Gaseosa Inca Kola",
            new BigDecimal("5.00"), new BigDecimal("7.00"),
            25, 8, "Unidad", "Activo", "gaseosa.jpg",
            "Bebidas", "Comercial Andina EIRL"));
}

    // LISTAR
    public List<Producto> listar() {
        return productos;
    }

    // BUSCAR
    public List<Producto> buscar(String q, Long idCat, Long idProv, String estado) {
    return productos.stream()
            .filter(p -> {
                // Filtro 1: texto libre
                if (q != null && !q.isBlank()) {
                    String f = q.toLowerCase();
                    boolean coincide =
                            p.getNomProd().toLowerCase().contains(f)
                            || (p.getNomCat() != null && p.getNomCat().toLowerCase().contains(f))
                            || (p.getNomProv() != null && p.getNomProv().toLowerCase().contains(f));
                    if (!coincide) return false;
                }
                // Filtro 2: categoría
                if (idCat != null && !p.getIdCat().equals(idCat)) return false;
                // Filtro 3: proveedor
                if (idProv != null && !p.getIdProv().equals(idProv)) return false;
                // Filtro 4: estado
                if (estado != null && !estado.isBlank()
                        && !estado.equalsIgnoreCase(p.getEstado())) return false;

                return true;
            })
            .collect(Collectors.toList());
}

    // CONSULTAR
    public Producto obtener(Long id) {
        return productos.stream()
                .filter(p -> p.getIdProd().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
    }

    // ADICIONAR / ACTUALIZAR
    public Producto guardar(Producto p) {
        if (p.getIdProd() == null) {
            p.setIdProd(contador.getAndIncrement());
            productos.add(p);
        } else {
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getIdProd().equals(p.getIdProd())) {
                    productos.set(i, p);
                    break;
                }
            }
        }
        return p;
    }

    // ELIMINAR
    public void eliminar(Long id) {
        productos.removeIf(p -> p.getIdProd().equals(id));
    }
}
