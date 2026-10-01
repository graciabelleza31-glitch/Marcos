package com.marcos.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.Categoria;

@Service 
public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public CategoriaService() {
        categorias.add(new Categoria(contador.getAndIncrement(), "Abarrotes", "Arroz, azúcar, fideos, aceites"));
        categorias.add(new Categoria(contador.getAndIncrement(), "Bebidas", "Gaseosas, jugos, aguas minerales"));
        categorias.add(new Categoria(contador.getAndIncrement(), "Lácteos", "Leche, quesos, yogures"));
        categorias.add(new Categoria(contador.getAndIncrement(), "Limpieza", "Detergentes, jabones, desinfectantes"));
    }

    // LISTAR
    public List<Categoria> listar() {
        return categorias;
    }

    // BUSCAR POR NOMBRE O DESCRIPCIÓN
    public List<Categoria> buscar(String q) {
        if (q == null || q.isBlank()) {
            return categorias;
        }
        String filtro = q.toLowerCase();
        return categorias.stream()
                .filter(c -> (c.getNomCat() != null && c.getNomCat().toLowerCase().contains(filtro))
                          || (c.getDescCat() != null && c.getDescCat().toLowerCase().contains(filtro)))
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public Categoria obtener(Long id) {
        return categorias.stream()
                .filter(c -> c.getIdCat().equals(id))
                .findFirst()
                .orElse(null);
    }

    // GUARDAR O ACTUALIZAR
    public Categoria guardar(Categoria cat) {
        if (cat.getIdCat() == null) {
            cat.setIdCat(contador.getAndIncrement());
            categorias.add(cat);
        } else {
            for (int i = 0; i < categorias.size(); i++) {
                if (categorias.get(i).getIdCat().equals(cat.getIdCat())) {
                    categorias.set(i, cat);
                    break;
                }
            }
        }
        return cat;
    }

    // ELIMINAR
    public void eliminar(Long id) {
        categorias.removeIf(c -> c.getIdCat().equals(id));
    }
}