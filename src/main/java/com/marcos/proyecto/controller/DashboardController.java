package com.marcos.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcos.proyecto.service.MovimientoService;
import com.marcos.proyecto.service.ProductoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public String dashboard(Model model, HttpSession session) {
        // Protección de ruta básica si no hay sesión
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        // Indicadores (Cards superiores)
        model.addAttribute("totalProductos", productoService.totalProductos());
        model.addAttribute("entradasHoy", movimientoService.totalEntradasHoy());
        model.addAttribute("salidasHoy", movimientoService.totalSalidasHoy());
        
        var productosStockBajo = productoService.obtenerStockBajo();
        model.addAttribute("productosStockBajo", productosStockBajo);
        model.addAttribute("cantStockBajo", productosStockBajo.size());

        // Tablas inferiores (Movimientos recientes y alertas)
        model.addAttribute("movimientosRecientes", movimientoService.listarUltimos());

        return "dashboard";
    }
}