package com.malsoft.ejemplo.repository;

import com.malsoft.ejemplo.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findFirstByNombre(String nombre);
}