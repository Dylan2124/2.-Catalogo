package com.Catalogo2._Catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoResponseDTO {

    private Long idProducto;
    private String nombre;
    private Integer precioUnitario;
    private String fabricante;
    private String categoriaNombre;
}
