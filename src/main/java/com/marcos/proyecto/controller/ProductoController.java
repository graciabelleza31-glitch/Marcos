package com.marcos.proyecto.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.marcos.proyecto.modelo.Proveedor;
import com.marcos.proyecto.service.CategoriaService;
import com.marcos.proyecto.service.ProductoService;
import com.marcos.proyecto.service.ProveedorService;


@Controller                                    
@RequestMapping("/productos")                  
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProveedorService proveedorService;

    // LISTA + BUSCADOR → http://localhost:8080/productos
    // LISTA + BUSCADOR + FILTROS
    @GetMapping
    public String lista(@RequestParam(required = false) String q,
                    @RequestParam(required = false) Long idCat,
                    @RequestParam(required = false) Long idProv,
                    @RequestParam(required = false) String estado,
                    Model model) {

        model.addAttribute("productos", productoService.buscar(q, idCat, idProv, estado));
        model.addAttribute("q", q);
        model.addAttribute("idCat", idCat);
        model.addAttribute("idProv", idProv);
        model.addAttribute("estado", estado);

        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("proveedores", proveedorService.listar());

        return "productos/lista";
    }

    private List<String> imagenesDisponibles() {
        try {
            Path carpeta = Paths.get("src/main/resources/static/images");
            if (!Files.exists(carpeta)) return List.of();

            return Files.list(carpeta)
                .filter(Files::isRegularFile)
                .map(p -> p.getFileName().toString())
                .sorted()
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }   
    }

    // FORM NUEVO → http://localhost:8080/productos/nuevo
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("proveedores", proveedorService.listar());
        model.addAttribute("imagenes", imagenesDisponibles());   // ← NUEVO
        return "productos/formulario";
    }

    // FORM EDITAR → http://localhost:8080/productos/editar/1
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.obtener(id));
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("proveedores", proveedorService.listar());
        model.addAttribute("imagenes", imagenesDisponibles());   // ← NUEVO
        return "productos/formulario";
    }

   // GUARDAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes flash) {
        Categoria cat = categoriaService.obtener(producto.getIdCat());
        if (cat != null) producto.setNomCat(cat.getNomCat());

        Proveedor prov = proveedorService.obtener(producto.getIdProv());
        if (prov != null) producto.setNomProv(prov.getNomProv());

        productoService.guardar(producto);
        flash.addFlashAttribute("msg", "Producto guardado correctamente");
        return "redirect:/productos";
    }

    // ELIMINAR → http://localhost:8080/productos/eliminar/1
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        productoService.eliminar(id);
        flash.addFlashAttribute("msg", "Producto eliminado");
        return "redirect:/productos";
    }
}