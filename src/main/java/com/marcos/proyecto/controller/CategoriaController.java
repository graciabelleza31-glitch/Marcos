package com.marcos.proyecto.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marcos.proyecto.modelo.Categoria;
import com.marcos.proyecto.modelo.Producto;
import com.marcos.proyecto.service.CategoriaService;
import com.marcos.proyecto.service.ProductoService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    // LISTA GENERAL DE CATEGORÍAS
    @GetMapping
    public String lista(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("categorias", categoriaService.buscar(q));
        model.addAttribute("q", q);
        return "categorias/lista";
    }

    // VER DETALLE: LISTA DE PRODUCTOS DE ESTA CATEGORÍA
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.obtener(id);
        if (categoria == null) {
            return "redirect:/categorias";
        }

        // Filtra únicamente los productos que pertenecen a esta categoría
        List<Producto> productosDeCategoria = productoService.listar().stream()
                .filter(p -> id.equals(p.getIdCat()))
                .collect(Collectors.toList());

        model.addAttribute("categoria", categoria);
        model.addAttribute("productos", productosDeCategoria);
        return "categorias/detalle";
    }

    // FORMULARIO NUEVA CATEGORÍA
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    // FORMULARIO EDITAR CATEGORÍA
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Categoria cat = categoriaService.obtener(id);
        if (cat == null) {
            return "redirect:/categorias";
        }
        model.addAttribute("categoria", cat);
        return "categorias/formulario";
    }

    // GUARDAR O ACTUALIZAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria, RedirectAttributes flash) {
        categoriaService.guardar(categoria);
        flash.addFlashAttribute("msg", "Categoría guardada exitosamente");
        return "redirect:/categorias";
    }

    // ELIMINAR CATEGORÍA
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        // Valida si tiene productos asociados para evitar inconsistencias
        boolean tieneProductos = productoService.listar().stream()
                .anyMatch(p -> id.equals(p.getIdCat()));

        if (tieneProductos) {
            flash.addFlashAttribute("error", "No se puede eliminar la categoría porque tiene productos asignados.");
            return "redirect:/categorias";
        }

        categoriaService.eliminar(id);
        flash.addFlashAttribute("msg", "Categoría eliminada correctamente");
        return "redirect:/categorias";
    }
}