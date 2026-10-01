package com.marcos.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.Proveedor;

@Service 
public class ProveedorService {

    private final List<Proveedor> proveedores = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public ProveedorService() {
        proveedores.add(new Proveedor(contador.getAndIncrement(), "Distribuidora Lima SAC", "20123456789", "987654321"));
        proveedores.add(new Proveedor(contador.getAndIncrement(), "Comercial Andina EIRL", "20987654321", "912345678"));
        proveedores.add(new Proveedor(contador.getAndIncrement(), "Abarrotes del Centro SRL", "20556677889", "955443322"));
    }

    // LISTAR
    public List<Proveedor> listar() {
        return proveedores;
    }

    // BUSCADOR POR NOMBRE O RUC
    public List<Proveedor> buscar(String q) {
        if (q == null || q.isBlank()) {
            return proveedores;
        }
        String filtro = q.toLowerCase();
        return proveedores.stream()
                .filter(p -> (p.getNomProv() != null && p.getNomProv().toLowerCase().contains(filtro))
                          || (p.getRucProv() != null && p.getRucProv().contains(filtro)))
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public Proveedor obtener(Long id) {
        return proveedores.stream()
                .filter(p -> p.getIdProv().equals(id))
                .findFirst()
                .orElse(null);
    }

    // GUARDAR O ACTUALIZAR
    public Proveedor guardar(Proveedor prov) {
        if (prov.getIdProv() == null) {
            prov.setIdProv(contador.getAndIncrement());
            proveedores.add(prov);
        } else {
            for (int i = 0; i < proveedores.size(); i++) {
                if (proveedores.get(i).getIdProv().equals(prov.getIdProv())) {
                    proveedores.set(i, prov);
                    break;
                }
            }
        }
        return prov;
    }

    // ELIMINAR
    public void eliminar(Long id) {
        proveedores.removeIf(p -> p.getIdProv().equals(id));
    }
}