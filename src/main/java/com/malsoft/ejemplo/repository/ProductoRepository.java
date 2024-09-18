package com.malsoft.ejemplo.repository;

import com.malsoft.ejemplo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository//Indica que esta clase es un repositorio
public interface ProductoRepository extends JpaRepository<Producto,Long> {

}
