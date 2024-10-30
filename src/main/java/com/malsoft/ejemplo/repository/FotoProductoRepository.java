package com.malsoft.ejemplo.repository;

import com.malsoft.ejemplo.entity.Foto;
import com.malsoft.ejemplo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoProductoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findAllByProducto(Producto producto);
}
