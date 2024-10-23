package com.malsoft.ejemplo.repository;

import com.malsoft.ejemplo.entity.Comentario;
import com.malsoft.ejemplo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByProductoOrderByFechaDesc(Producto producto);

}
