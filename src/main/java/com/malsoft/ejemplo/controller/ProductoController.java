package com.malsoft.ejemplo.controller;

import com.malsoft.ejemplo.entity.Categoria;
import com.malsoft.ejemplo.entity.Producto;
import com.malsoft.ejemplo.repository.CategoriaRepository;
import com.malsoft.ejemplo.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Para que Spring sepa que esta clase es un controlador tenemos que añadir la anotación @Controller antes de la clase
@Controller
public class ProductoController {

    private final CategoriaRepository categoriaRepository;
    //Para acceder al repositorio creamos una propiedad y la asignamos en el constructor
    private ProductoRepository productoRepository;

    public ProductoController(ProductoRepository repository, CategoriaRepository categoriaRepository){
        this.productoRepository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/productos2")    //Anotación que indica la URL localhost:8080/productos2 mediante GET
    @ResponseBody       //Anotación que indica que no pase por el motor de plantillas thymeleaf sino que voy a devolver yo el HTML diréctamente
    public String index(){
        List<com.malsoft.ejemplo.entity.Producto> productos = this.productoRepository.findAll();
        StringBuilder HTML = new StringBuilder("<html><body>");
        productos.forEach(producto -> {
            HTML.append("<p>" + producto.getTitulo() + "</p>");
        });
        HTML.append("</body></html>");

        return HTML.toString();
    }

    /* Con la anotación GetMapping le indicamos a Spring que el siguiente método
       se va a ejecutar cuando el usuario acceda a la URL http://localhost/productos */
    @GetMapping("/productos")
    //Model se usa para pasar datos desde el controlador a la vista
    public String findAll(Model model){
        List<Producto> productos = this.productoRepository.findAll();
        List<Categoria> categorias = this.categoriaRepository.findAll();
        //Pasamos los datos a la vista
        model.addAttribute("productos",productos);
        model.addAttribute("categorias",categorias);

        return "producto-list";
    }

    @GetMapping("/productos/add")
    public String add(){
        List<com.malsoft.ejemplo.entity.Producto> productos = new ArrayList<com.malsoft.ejemplo.entity.Producto>();
        com.malsoft.ejemplo.entity.Producto p1 = new com.malsoft.ejemplo.entity.Producto(null, "Producto 1", 20, 45.5);
        com.malsoft.ejemplo.entity.Producto p2 = new com.malsoft.ejemplo.entity.Producto(null, "Producto 2", 50, 5.0);
        com.malsoft.ejemplo.entity.Producto p3 = new com.malsoft.ejemplo.entity.Producto(null, "Producto 3", 30, 50.5);
        com.malsoft.ejemplo.entity.Producto p4 = new com.malsoft.ejemplo.entity.Producto(null, "Producto 4", 10, 30.0);
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);

        //Guardamos todos los productos en la base de datos utilizando el objeto productoRepository
        this.productoRepository.saveAll(productos);

        //Redirige al controlador /productos
        return "redirect:/productos";

    }
    //En el mapping ponemos entre {} la variable que queremos para darle a
    //la función mediante la ruta
    @GetMapping("/productos/del/{id}")
    public String delete(@PathVariable Long id){
        productoRepository.deleteById(id);
        //borrar productos
        return "redirect:/productos";
    }
    @GetMapping("/productos/view/{id}")
    public String view(@PathVariable Long id, Model model){
        Producto p = productoRepository.getReferenceById(id);
        model.addAttribute("producto",p);
        return "producto-view";
    }
    @GetMapping("/productos/new")
    public String newProducto(Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto-new";
    }
    @PostMapping("/productos/new")
    //RequiestParam recoge los parámetros de un formulario por su name
    //Aunque se puede crear directamete el producto en el formulario
    //Abria que crear en el getMapping un producto vacio con model.addAtribute()
    public String newProductoPOST(@Valid Producto p, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "producto-new";
        }
        productoRepository.save(p);
        //Redirigimos a /productos
        return "redirect:/productos";
    }
    @GetMapping("/productos/edit/{id}")
    public String redirectProducto(@PathVariable Long id,Model model){
        Producto producto = productoRepository.getReferenceById(id);
        model.addAttribute("producto",producto);
        return "producto-edit";
    }
    @PostMapping("/productos/edit/{id}")
    public String editProducto(@PathVariable Long id,Producto p){
        p.setId(id);
        productoRepository.save(p);
        return "redirect:/productos";
    }
    @GetMapping("/productos/categoria/{id}")
    //Model se usa para pasar datos desde el controlador a la vista
    public String findAllCategories(Model model,@PathVariable Long id){
        Optional<Categoria> categoriaSeleccionada= categoriaRepository.findById(id);
        if(categoriaSeleccionada.isPresent()) {
            List<Producto> productos = this.productoRepository.findByCategoria(categoriaSeleccionada.get());
            List<Categoria> categorias = this.categoriaRepository.findAll();
            //Pasamos los datos a la vista
            model.addAttribute("selectedCategoriaId",id);
            model.addAttribute("productos", productos);
            model.addAttribute("categorias", categorias);

            return "producto-list";
        }else {
            //La categoría seleccionada no existe
            return "redirect:/productos";
        }
    }

}
