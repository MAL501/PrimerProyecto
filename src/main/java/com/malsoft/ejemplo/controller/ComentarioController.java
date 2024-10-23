package com.malsoft.ejemplo.controller;

import com.malsoft.ejemplo.entity.Comentario;
import com.malsoft.ejemplo.entity.Producto;
import com.malsoft.ejemplo.repository.ComentarioRepository;
import com.malsoft.ejemplo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class ComentarioController {

    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/productos/view/{idProducto}/comentarios/add")
    public String AddComentario(@ModelAttribute Comentario comentario,
                                @PathVariable Long idProducto
    ) {

        comentario.setFecha(LocalDate.now());
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        comentario.setProducto(producto);
        comentarioRepository.save(comentario);
        return "redirect:/productos/view/"+idProducto;
    }
}
