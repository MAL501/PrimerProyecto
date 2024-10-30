package com.malsoft.ejemplo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoProductoDTO {
    private Long id;
    private String ruta;
    private String productoId;
}
