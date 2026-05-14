package com.Catalogo2._Catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacio.")
    private String nombre;

    @NotNull(message = "El nombre de la categoria es obligatoria")
    private String categoria;

    @NotNull(message = "El precio del producto debe ser mayor a 0.")
    @NotBlank(message = "El Id de la categoria es obligatorio")
    private Integer precioUnitario;

    @NotBlank(message = "El fabricante es obligatorio.")
    private String fabricante;



}
