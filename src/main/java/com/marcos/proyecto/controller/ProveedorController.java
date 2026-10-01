package com.marcos.proyecto.controller;

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

import com.marcos.proyecto.modelo.Proveedor;
import com.marcos.proyecto.service.ProveedorService;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    
    @GetMapping
    public String lista(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("proveedores", proveedorService.buscar(q));
        model.addAttribute("q", q);
        return "proveedores/lista";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/formulario";
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Proveedor prov = proveedorService.obtener(id);
        if (prov == null) {
            return "redirect:/proveedores";
        }
        model.addAttribute("proveedor", prov);
        return "proveedores/formulario";
    }

    // GUARDAR (CREACIÓN O EDICIÓN)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Proveedor proveedor, RedirectAttributes flash) {
        proveedorService.guardar(proveedor);
        flash.addFlashAttribute("msg", "Proveedor guardado exitosamente");
        return "redirect:/proveedores";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        proveedorService.eliminar(id);
        flash.addFlashAttribute("msg", "Proveedor eliminado correctamente");
        return "redirect:/proveedores";
    }
}