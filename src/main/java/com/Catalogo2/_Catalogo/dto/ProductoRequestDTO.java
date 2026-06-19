package com.Catalogo2._Catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "ProductoRequest",
        description = "DTO para crear o actualizar un producto"
)
public class ProductoRequestDTO {

    @Schema(
            description = "Nombre completo del producto",
            example = "Laptop HP Pavilion 15",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El nombre del producto no puede estar vacio.")
    private String nombre;

    @Schema(
            description = "Categoria del producto",
            example = "Electrónica",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El nombre de la categoria es obligatoria")
    private String categoria;

    @NotNull(message = "El precio del producto debe ser mayor a 0.")
    @Min(value = 1,message = "El precio del producto debe ser mayor a 0")
    private Integer precioUnitario;

    @Schema(
            description = "Fabricante del producto",
            example = "HP",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El fabricante es obligatorio.")
    private String fabricante;

    @Schema(
            description = "ID de la especificacion asociada al producto",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    @NotNull(message = "El ID del la especificion es obligatoria")
    private  Long especificacionId;



}
