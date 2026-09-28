package com.marcos.proyecto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.Proveedor;

@Service 
public class ProveedorService {
    private final List<Proveedor> proveedores = new ArrayList<>();

    public ProveedorService() {
        proveedores.add(new Proveedor(1L, "Distribuidora Lima SAC", "20123456789", "987654321"));
        proveedores.add(new Proveedor(2L, "Comercial Andina EIRL", "20987654321", "912345678"));
    }

    public List<Proveedor> listar() { return proveedores; }

    public Proveedor obtener(Long id) {
        return proveedores.stream()
                .filter(p -> p.getIdProv().equals(id))
                .findFirst()
                .orElse(null);
    }
}
