package com.Catalogo2._Catalogo.dto;

import jakarta.validation.constraints.Min;
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
    @Min(value = 1,message = "El precio del producto debe ser mayor a 0")
    private Integer precioUnitario;

    @NotBlank(message = "El fabricante es obligatorio.")
    private String fabricante;

    @NotNull(message = "El ID del la especificion es obligatoria")
    private  Long especificacionId;



}
