package com.malsoft.ejemplo.entity;

import jakarta.persistence.*;

@Entity //Indica que esta clase es una entidad
@Table(name="productos")//Indica que la tabla en la base de datos relacionada con esta entidad
public class Producto {
    @Id //Esta notación especifica que este campo va a ser la clave principal de la tabla en la BBDD
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Esta anotación especifica qie la clave primaria sea "auto-increment"
    private Long id;
    private String titulo;
    private Integer cantidad;
    private Double precio;

    public Producto(String titulo, Integer cantidad, Double precio, Long id) {
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id = id;
    }

    public Producto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
