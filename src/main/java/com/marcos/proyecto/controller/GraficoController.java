package com.marcos.proyecto.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcos.proyecto.modelo.Producto;
import com.marcos.proyecto.service.ProductoService;

@Controller
@RequestMapping("/graficos")
public class GraficoController {

    @Autowired
    private ProductoService productoService;

    // 1. GRÁFICO DE BARRAS: STOCK ACTUAL VS MÍNIMO
    @GetMapping("/barras")
    public String graficosBarras(Model model) {
        List<Producto> productos = productoService.listar();

        List<String> nombres = productos.stream().map(Producto::getNomProd).collect(Collectors.toList());
        List<Integer> stockActual = productos.stream().map(p -> p.getStockProd() != null ? p.getStockProd() : 0).collect(Collectors.toList());
        List<Integer> stockMinimo = productos.stream().map(p -> p.getStockMinimo() != null ? p.getStockMinimo() : 0).collect(Collectors.toList());

        model.addAttribute("nombres", nombres);
        model.addAttribute("stockActual", stockActual);
        model.addAttribute("stockMinimo", stockMinimo);

        return "graficos/barras";
    }

    // 2. GRÁFICO CIRCULAR: DISTRIBUCIÓN POR CATEGORÍA
    @GetMapping("/circular")
    public String graficoCircular(Model model) {
        List<Producto> productos = productoService.listar();

        // Agrupación por categoría y conteo de productos
        Map<String, Long> conteoPorCat = productos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getNomCat() != null ? p.getNomCat() : "Sin Categoría",
                        Collectors.counting()
                ));

        List<String> categorias = List.copyOf(conteoPorCat.keySet());
        List<Long> cantidades = List.copyOf(conteoPorCat.values());

        model.addAttribute("categorias", categorias);
        model.addAttribute("cantidades", cantidades);

        return "graficos/circular";
    }
}