package com.malsoft.ejemplo.repository;

import com.malsoft.ejemplo.entity.Categoria;
import com.malsoft.ejemplo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository//Indica que esta clase es un repositorio
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByCategoria(Categoria categoria);
    Long countByCategoria(Categoria categoria); //Número de productos en una categoría

    @Query("SELECT AVG(p.precio) FROM Producto p WHERE p.categoria.id = :categoriaId")
    Double AVGPrecioByCategoria(@Param("categoriaId") Long categoriaId);
}
