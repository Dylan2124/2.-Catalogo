package com.Catalogo2._Catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ProductoResponse",
        description = "DTO de respuesta que contiene la información pública del producto"
)
public class ProductoResponseDTO {

    @Schema(
            description = "Identificador único del producto en el sistema",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idProducto;
    @Schema(
            description = "Nombre completo del producto",
            example = "Laptop HP Pavilion 15",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombre;
    @Schema(
            description = "Precio unitario del producto",
            example = "1500",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer precioUnitario;
    @Schema(
            description = "Fabricante del producto",
            example = "HP",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fabricante;
    @Schema(
            description = "Categoria del producto",
            example = "Electrónica",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String categoria;
}
