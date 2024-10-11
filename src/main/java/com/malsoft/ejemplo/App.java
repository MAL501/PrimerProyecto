package com.malsoft.ejemplo;

import com.malsoft.ejemplo.entity.Categoria;
import com.malsoft.ejemplo.entity.Comentario;
import com.malsoft.ejemplo.entity.Producto;
import com.malsoft.ejemplo.repository.CategoriaRepository;
import com.malsoft.ejemplo.repository.ProductoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class App {

	public static void main(String[] args) {

		ApplicationContext contex = SpringApplication.run(App.class, args);

		//Así podemos acceder a un Bean de Spring para, por ejemplo, insertar datos de ejemplo nada más ejecutar la app

		ProductoRepository productoRepository = contex.getBean(ProductoRepository.class);
		CategoriaRepository categoriaRepository = contex.getBean(CategoriaRepository.class);

		Producto p1 = new Producto(null, "t1",10,50.5);
		Producto p2 = new Producto(null, "t2",10,50.5);
		Categoria c=new Categoria(null,"Móviles","Patatas");
		Comentario comen=new Comentario(0,"patata","es una patata", LocalDateTime.now(),p1);
		p1.setCategoria(c);
		p2.setCategoria(c);
		c.getProductos().add(p1);
		c.getProductos().add(p2);
	}

}
