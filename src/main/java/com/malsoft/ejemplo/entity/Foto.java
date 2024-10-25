package com.malsoft.ejemplo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Foto {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String ruta;
    @ManyToOne(targetEntity = Producto.class)
    private Producto producto;

}
