package com.marcos.proyecto.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marcos.proyecto.modelo.DetalleVenta;
import com.marcos.proyecto.modelo.Usuario;
import com.marcos.proyecto.modelo.Venta;
import com.marcos.proyecto.service.CategoriaService;
import com.marcos.proyecto.service.ProductoService;
import com.marcos.proyecto.service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/nueva")
    public String nuevaVenta(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        String nomVendedor = (usuario != null) ? usuario.getNomUs() : "Vendedor Mostrador";

        // Número correlativo estimado para la vista
        long proximoId = ventaService.listar().size() + 1;
        String nroVenta = String.format("VEN-%05d", proximoId);

        model.addAttribute("nroVenta", nroVenta);
        model.addAttribute("fechaHoy", LocalDate.now());
        model.addAttribute("vendedor", nomVendedor);

        // Catálogos para el panel izquierdo y selects
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("categorias", categoriaService.listar());

        return "ventas/nueva";
    }

    @PostMapping("/guardar")
    public String guardarVenta(@RequestParam(required = false) String nomCliente,
                               @RequestParam(required = false) String numDocumentoCliente, // <-- Recibir DNI/RUC
                               @RequestParam(required = false) String nroVenta,
                               @RequestParam(defaultValue = "0") BigDecimal descuento,
                               @RequestParam(value = "idProd", required = false) List<Long> idsProd,
                               @RequestParam(value = "cantDetalle", required = false) List<Integer> cantidades,
                               HttpSession session,
                               RedirectAttributes flash) {
        try {
            if (idsProd == null || idsProd.isEmpty()) {
                throw new IllegalArgumentException("Debe agregar al menos un producto a la lista de venta.");
            }

            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

            Venta venta = new Venta();
            venta.setIdUs(usuario != null ? usuario.getIdUs() : 1L);
            venta.setNomCliente((nomCliente != null && !nomCliente.isBlank()) ? nomCliente : "Público General");
            venta.setNumDocumentoCliente((numDocumentoCliente != null && !numDocumentoCliente.isBlank()) ? numDocumentoCliente : "Sin Documento"); // <-- Asignar
            venta.setMetodoPago("Efectivo");

            for (int i = 0; i < idsProd.size(); i++) {
                Long id = idsProd.get(i);
                Integer cant = (cantidades != null && i < cantidades.size()) ? cantidades.get(i) : 1;
                if (id != null && cant != null && cant > 0) {
                    DetalleVenta d = new DetalleVenta();
                    d.setIdProd(id);
                    d.setCantDetalle(cant);
                    venta.getDetalles().add(d);
                }
            }

            Venta guardada = ventaService.procesarVenta(venta);
            flash.addFlashAttribute("msg", "Venta registrada con éxito.");
            return "redirect:/ventas/boleta/" + guardada.getIdVenta();

        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/ventas/nueva";
        }
    }

    @GetMapping("/boleta/{id}")
    public String verBoleta(@PathVariable Long id, Model model) {
        Venta v = ventaService.obtener(id);
        if (v == null) return "redirect:/ventas/nueva";
        model.addAttribute("venta", v);
        return "ventas/boleta";
    }
}