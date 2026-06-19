package com.Catalogo2._Catalogo.controller;

import com.Catalogo2._Catalogo.dto.ProductoRequestDTO;
import com.Catalogo2._Catalogo.dto.ProductoResponseDTO;
import com.Catalogo2._Catalogo.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Tag(name = "Productos", description = "Operaciones para gestionar productos y búsquedas")
@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @Operation(
        summary = "Listar todos los productos",
        description = "Devuelve la lista completa de productos con hipervínculos HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductoResponseDTO>>> obtenerTodo(){
        List<ProductoResponseDTO> productos = productoService.obtenerTodo();
        List<EntityModel<ProductoResponseDTO>> list = productos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("todos-productos")
                ))
                .toList();

        CollectionModel<EntityModel<ProductoResponseDTO>> collection = CollectionModel.of(list,
                linkTo(methodOn(ProductoController.class).obtenerTodo()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @Operation(
        summary = "Obtener un producto por ID",
        description = "Devuelve un producto específico con enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoResponseDTO>> obtenerPorId(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id){
        return productoService.obtenerPorId(id)
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea y devuelve un nuevo producto con hipervínculo al recurso creado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<ProductoResponseDTO>> guardarProducto(@Valid @RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO nuevoProducto = productoService.guardar(dto);
        EntityModel<ProductoResponseDTO> resource = EntityModel.of(nuevoProducto,
                linkTo(methodOn(ProductoController.class).obtenerPorId(nuevoProducto.getIdProducto())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @Operation(
        summary = "Actualizar un producto existente",
        description = "Actualiza un producto específico y devuelve sus datos modificados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoResponseDTO>> actualizar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto){
        return productoService.actualizar(id, dto)
                .map(p -> EntityModel.of(p,
                        linkTo(methodOn(ProductoController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina un producto específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable("id") Long idProducto){
        if(productoService.obtenerPorId(idProducto).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        productoService.eliminar(idProducto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Buscar productos por nombre",
        description = "Devuelve una lista de productos que coincidan con el nombre especificado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda completada",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Error en la búsqueda")
    })
    @GetMapping("/buscar")
    public ResponseEntity<CollectionModel<EntityModel<ProductoResponseDTO>>> buscarPorNombre(
            @Parameter(description = "Nombre del producto a buscar", example = "Camiseta")
            @RequestParam("nombre") String nombre){
        List<ProductoResponseDTO> productos = productoService.buscarPorNombre(nombre);
        List<EntityModel<ProductoResponseDTO>> list = productos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("todos-productos")
                ))
                .toList();

        CollectionModel<EntityModel<ProductoResponseDTO>> collection = CollectionModel.of(list,
                linkTo(methodOn(ProductoController.class).buscarPorNombre(nombre)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @Operation(
        summary = "Buscar productos por fabricante",
        description = "Devuelve una lista de productos del fabricante especificado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda completada"),
        @ApiResponse(responseCode = "500", description = "Error en la búsqueda")
    })
    @GetMapping("/fabricante")
    public ResponseEntity<CollectionModel<EntityModel<ProductoResponseDTO>>> buscarFabricante(
            @Parameter(description = "Nombre del fabricante", example = "Samsung")
            @RequestParam String fabricante){
        List<ProductoResponseDTO> productos = productoService.buscarPorFabricante(fabricante);
        List<EntityModel<ProductoResponseDTO>> list = productos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("todos-productos")
                ))
                .toList();

        CollectionModel<EntityModel<ProductoResponseDTO>> collection = CollectionModel.of(list,
                linkTo(methodOn(ProductoController.class).buscarFabricante(fabricante)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @Operation(
        summary = "Buscar productos por categoría",
        description = "Devuelve una lista de productos de la categoría especificada"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda completada"),
        @ApiResponse(responseCode = "500", description = "Error en la búsqueda")
    })
    @GetMapping("/categoria")
    public ResponseEntity<CollectionModel<EntityModel<ProductoResponseDTO>>> buscarCategoria(
            @Parameter(description = "Nombre de la categoría", example = "Electrónica")
            @RequestParam String categoria){
        List<ProductoResponseDTO> productos = productoService.buscarPorCategoria(categoria);
        List<EntityModel<ProductoResponseDTO>> list = productos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("todos-productos")
                ))
                .toList();

        CollectionModel<EntityModel<ProductoResponseDTO>> collection = CollectionModel.of(list,
                linkTo(methodOn(ProductoController.class).buscarCategoria(categoria)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @Operation(
        summary = "Obtener el producto más barato",
        description = "Devuelve el producto con el precio más bajo disponible"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "No hay productos disponibles")
    })
    @GetMapping("/barato")
    public ResponseEntity<EntityModel<ProductoResponseDTO>> buscarEconomico(){
        return productoService.obtenerProductoMasBarato()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).buscarEconomico()).withSelfRel(),
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Obtener sugerencia aleatoria",
        description = "Devuelve una lista aleatoria de productos como sugerencia"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sugerencias devueltas")
    })
    @GetMapping("/sugerencia")
    public ResponseEntity<CollectionModel<EntityModel<ProductoResponseDTO>>> buscarSugerencia(){
        List<ProductoResponseDTO> productos = productoService.buscarSugerenciaAleatorio();
        List<EntityModel<ProductoResponseDTO>> list = productos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("todos-productos")
                ))
                .toList();

        CollectionModel<EntityModel<ProductoResponseDTO>> collection = CollectionModel.of(list,
                linkTo(methodOn(ProductoController.class).buscarSugerencia()).withSelfRel());

        return ResponseEntity.ok(collection);
    }




}
