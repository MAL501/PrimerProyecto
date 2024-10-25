package com.malsoft.ejemplo.service;

import com.malsoft.ejemplo.entity.Comentario;
import com.malsoft.ejemplo.entity.Producto;
import com.malsoft.ejemplo.repository.ComentarioRepository;
import com.malsoft.ejemplo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ComentarioService {
    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    private ProductoRepository productoRepository;
    public void addComentario(Long id, Comentario comentario){
        comentario.setFecha(LocalDate.now());
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        comentario.setProducto(producto);
    }
}
