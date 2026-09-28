package com.marcos.proyecto.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marcos.proyecto.modelo.Usuario;
import com.marcos.proyecto.service.MovimientoService;
import com.marcos.proyecto.service.ProductoService;
import com.marcos.proyecto.service.ProveedorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    // FORMULARIO: ENTRADA DE MERCANCÍA
    @GetMapping
    public String nuevaEntrada(Model model) {
        model.addAttribute("fechaHoy", LocalDate.now());
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("proveedores", proveedorService.listar());
        return "movimientos/formulario";
    }

    // PROCESAR ENTRADA
    @PostMapping("/guardar")
    public String guardarEntrada(@RequestParam(required = false) String nroReferencia,
                                 @RequestParam(required = false) Long idProv,
                                 @RequestParam(required = false) String observaciones,
                                 @RequestParam(value = "idProd", required = false) List<Long> idsProd,
                                 @RequestParam(value = "cantMov", required = false) List<Integer> cantidades,
                                 HttpSession session,
                                 RedirectAttributes flash) {
        try {
            if (idsProd == null || idsProd.isEmpty()) {
                throw new IllegalArgumentException("Debe agregar al menos un producto a la entrada de mercadería.");
            }

            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            Long idUs = (usuario != null) ? usuario.getIdUs() : 1L;

            String motivoCompleto = (nroReferencia != null && !nroReferencia.isBlank() ? "[Guía/Doc: " + nroReferencia + "] " : "")
                    + (observaciones != null ? observaciones : "");

            // Registrar cada producto e incrementar stock
            for (int i = 0; i < idsProd.size(); i++) {
                Long id = idsProd.get(i);
                Integer cant = (cantidades != null && i < cantidades.size()) ? cantidades.get(i) : 0;

                if (id != null && cant != null && cant > 0) {
                    movimientoService.registrarMovimiento(id, "ENTRADA", cant, motivoCompleto, idUs);
                }
            }

            flash.addFlashAttribute("msg", "Entrada de mercancía registrada. Stock incrementado exitosamente.");
            return "redirect:/dashboard";

        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/movimientos";
        }
    }
}