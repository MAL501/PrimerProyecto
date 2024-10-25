package com.malsoft.ejemplo.controller;

import com.malsoft.ejemplo.DTO.CategoriaCosteMedioDTO;
import com.malsoft.ejemplo.entity.Categoria;
import com.malsoft.ejemplo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping ("/categorias")
    public String categoria(Model model) {
        //Con esto obtendríamos todas las categorías
        //List<Categoria> categorias  = categoriaRepository.findAll();
        //Con esto hacemos una consulta personalizada para obtener el coste medio y número de productos por categoria
        List<CategoriaCosteMedioDTO> categoriasConStats = categoriaService.obtenerCategoriasConStats();
        model.addAttribute("categorias", categoriasConStats);
        return "categoria-list";
    }

    @GetMapping("/categoria/delete/{id}")
    public String borrarCategoria(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias";  // Redirige de nuevo a la lista de categorías
    }
    @GetMapping("/categorias/new")
    public String crearCategoria(Model model) {
        model.addAttribute("categoria",new Categoria());
        return "categoria-new";
    }
    @PostMapping("/categorias/new")
    public String guardarCategoria( @ModelAttribute("categoria") Categoria categoria,Model model,
                                    @RequestAttribute("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {
        try {
            categoriaService.guardarCategoria(categoria, file);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/categorias/new";
        }
        return "redirect:/categorias";
    }
}
