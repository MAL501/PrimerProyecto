package com.malsoft.ejemplo.controller;

import com.malsoft.ejemplo.entity.Categoria;
import com.malsoft.ejemplo.entity.Comentario;
import com.malsoft.ejemplo.entity.Producto;
import com.malsoft.ejemplo.repository.CategoriaRepository;
import com.malsoft.ejemplo.repository.ComentarioRepository;
import com.malsoft.ejemplo.repository.ProductoRepository;
import com.malsoft.ejemplo.service.FotoProductoService;
import com.malsoft.ejemplo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Para que Spring sepa que esta clase es un controlador tenemos que añadir la anotación @Controller antes de la clase
@Controller
public class ProductoController {
    private final ProductoService productoService;
    private final FotoProductoService fotoProductoService;

    @Autowired
    public ProductoController(ProductoService productoService, FotoProductoService fotoProductoService) {
        this.productoService = productoService;
        this.fotoProductoService = fotoProductoService;
    }

    @GetMapping("/productos")
    public String findAll(Model model) {
        model.addAttribute("productos", productoService.findAllProductos());
        model.addAttribute("categorias", productoService.findAllCategorias());
        model.addAttribute("titulo", "Titulo de página");
        return "producto-list";
    }

    @GetMapping("/productos/categoria/{id}")
    public String findAllByCategoria(Model model, @PathVariable Long id) {
        if (id.equals(-1L)) {
            return "redirect:/productos";
        }
        Optional<Categoria> categoriaSeleccionada = productoService.findCategoriaById(id);
        if (categoriaSeleccionada.isPresent()) {
            model.addAttribute("selectedCategoriaId", id);
            model.addAttribute("productos", productoService.findProductosByCategoria(categoriaSeleccionada.get()));
            model.addAttribute("categorias", productoService.findAllCategorias());
            return "producto-list";
        }
        return "redirect:/productos";
    }

    @GetMapping("/productos/del/{id}")
    public String delete(@PathVariable Long id) {
        productoService.deleteProductoById(id);
        return "redirect:/productos";
    }

    @GetMapping("/productos/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            model.addAttribute("comentarios", productoService.findComentariosByProducto(producto.get()));
            model.addAttribute("comentario", new Comentario());
            return "producto-view";
        }
        return "redirect:/productos";
    }

    @GetMapping("/productos/new")
    public String newProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", productoService.findAllCategorias());
        return "producto-new";
    }

    @PostMapping("/productos/new")
    public String newProductoInsert(Model model, @Valid Producto producto,
                                    BindingResult bindingResult, @RequestParam("archivosFotos") List<MultipartFile> fotos) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", productoService.findAllCategoriasSorted());
            return "producto-new";
        }

        //Guardar fotos
        fotoProductoService.guardarFotos(fotos, producto);

        //Guardar producto
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/edit/{id}")
    public String editProducto(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "producto-edit";
        }
        return "redirect:/productos";
    }

    @PostMapping("/productos/edit/{id}")
    public String editProductoUpdate(@PathVariable Long id, Producto producto) {
        producto.setId(id);
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }
}
