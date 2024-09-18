package com.malsoft.ejemplo.controller;

import com.malsoft.ejemplo.entity.Producto;
import com.malsoft.ejemplo.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller //Anotación que indica a Spring que esta clase es un controlador
public class ProductoController {
    //Para acceder al repositorio creamos una propiedad y ñe asignamos un constructor
    private ProductoRepository productoRepository;
    public ProductoController(ProductoRepository repository){
        this.productoRepository = repository;
    }

    @GetMapping("/productos")
    /*Con la anotación GetMapping le indicamos a Spring que el siguiente método
    se va a ejecutar cuando el usuario acceda a la URL GET http://localhost/productos*/
    public String findAll() {
        List<Producto> productos=this.productoRepository.findAll();
        return "producto-list";
    }

    @GetMapping("/productos/add")
    /*Con la anotación GetMapping le indicamos a Spring que el siguiente método
    se va a ejecutar cuando el usuario acceda a la URL GET http://localhost/productos*/
    public String add() {
        List<Producto> productos = new ArrayList<Producto>();
        Producto p1 = new Producto("producto 1",102,452.5,null);
        Producto p2 = new Producto("producto 2",310,345.5,null);
        Producto p3 = new Producto("producto 3",410,445.5,null);
        Producto p4 = new Producto("producto 4",210,455.5,null);
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);
        //Guardamos todos los productos en la BBDD utilizando el objeto productoRepository
        this.productoRepository.saveAll(productos);
        //Redirige al controlador /productos
        //IMPORTANTE: PONER EL REDIRECT CON UNA SOLA /
        return "redirect:/productos";
    }
}
