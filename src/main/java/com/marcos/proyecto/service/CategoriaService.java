package com.marcos.proyecto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.Categoria;

@Service 
public class CategoriaService {
    private final List<Categoria> categorias = new ArrayList<>();

    public CategoriaService() {
        categorias.add(new Categoria(1L, "Abarrotes", "Arroz, azúcar, aceite"));
        categorias.add(new Categoria(2L, "Bebidas", "Gaseosas, jugos"));
        categorias.add(new Categoria(3L, "Lácteos", "Leche, yogur"));
    }

    public List<Categoria> listar() { return categorias; }

    public Categoria obtener(Long id) {
        return categorias.stream()
                .filter(c -> c.getIdCat().equals(id))
                .findFirst()
                .orElse(null);
    }
}
