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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Tag(name = "Productos", description = "Operaciones para gestionar productos y búsquedas")
@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @Operation(summary = "Listar todos los productos", description = "Devuelve la lista completa de productos con hipervínculos HATEOAS")
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodo(){
        List<ProductoResponseDTO> productos = productoService.obtenerTodo();
        List<EntityModel<ProductoResponseDTO>> list = productos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerPorId(dto.getIdProducto())).withSelfRel().withTitle("Ver este producto"),
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("todos-productos")
                ))
                .toList();

        CollectionModel<EntityModel<ProductoResponseDTO>> collection = CollectionModel.of(list,
                linkTo(methodOn(ProductoController.class).obtenerTodo()).withSelfRel());

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Catálogo completo de productos recuperado exitosamente.");
        respuesta.put("total", productos.size());
        respuesta.put("resultado", collection);

        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Obtener un producto por ID", description = "Devuelve un producto específico con un mensaje de éxito")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long id){
        return productoService.obtenerPorId(id)
                .map(dto -> {
                    EntityModel<ProductoResponseDTO> resource = EntityModel.of(dto,
                            linkTo(methodOn(ProductoController.class).obtenerPorId(id)).withSelfRel(),
                            linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos"),
                            linkTo(methodOn(ProductoController.class).eliminar(id)).withRel("eliminar")
                    );

                    Map<String, Object> respuesta = new LinkedHashMap<>();
                    respuesta.put("mensaje", "Producto con ID " + id + " encontrado correctamente.");
                    respuesta.put("resultado", resource);
                    return ResponseEntity.ok(respuesta);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea y devuelve un nuevo producto junto a un mensaje de confirmación")
    @PostMapping
    public ResponseEntity<Map<String, Object>> guardarProducto(@Valid @RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO nuevoProducto = productoService.guardar(dto);
        EntityModel<ProductoResponseDTO> resource = EntityModel.of(nuevoProducto,
                linkTo(methodOn(ProductoController.class).obtenerPorId(nuevoProducto.getIdProducto())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos")
        );

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "El producto '" + nuevoProducto.getNombre() + "' ha sido creado exitosamente.");
        respuesta.put("resultado", resource);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Actualizar un producto existente", description = "Actualiza un producto específico y devuelve un mensaje de confirmación")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto){
        return productoService.actualizar(id, dto)
                .map(p -> {
                    EntityModel<ProductoResponseDTO> resource = EntityModel.of(p,
                            linkTo(methodOn(ProductoController.class).obtenerPorId(id)).withSelfRel(),
                            linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos")
                    );

                    Map<String, Object> respuesta = new LinkedHashMap<>();
                    respuesta.put("mensaje", "El producto con ID " + id + " se ha actualizado correctamente.");
                    respuesta.put("resultado", resource);
                    return ResponseEntity.ok(respuesta);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto específico devolviendo confirmación en JSON")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(
            @Parameter(description = "ID del producto", example = "1") @PathVariable("id") Long idProducto){
        if(productoService.obtenerPorId(idProducto).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        productoService.eliminar(idProducto);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El producto con ID " + idProducto + " ha sido eliminado correctamente.");

        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Filtrar productos dinámicamente",
            description = "Permite buscar productos aplicando un filtro opcional por nombre, fabricante o categoría."
    )
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String fabricante,
            @RequestParam(required = false) String categoria){

        List<ProductoResponseDTO> productos;
        String mensaje;

        // Lógica condicional para ejecutar la búsqueda correcta según el parámetro enviado
        if (nombre != null && !nombre.isBlank()) {
            productos = productoService.buscarPorNombre(nombre);
            mensaje = "Búsqueda por nombre '" + nombre + "' realizada con éxito.";
        } else if (fabricante != null && !fabricante.isBlank()) {
            productos = productoService.buscarPorFabricante(fabricante);
            mensaje = "Productos del fabricante '" + fabricante + "' recuperados con éxito.";
        } else if (categoria != null && !categoria.isBlank()) {
            productos = productoService.buscarPorCategoria(categoria);
            mensaje = "Productos en la categoría '" + categoria + "' recuperados con éxito.";
        } else {
            productos = productoService.obtenerTodo();
            mensaje = "No se proporcionaron criterios específicos. Mostrando todo el catálogo.";
        }

        return procesarBusquedaColeccion(productos, mensaje,
                linkTo(methodOn(ProductoController.class).buscarProductos(nombre, fabricante, categoria)).withSelfRel());
    }

    @Operation(summary = "Obtener el producto más barato", description = "Devuelve el producto con el precio más bajo disponible")
    @GetMapping("/barato")
    public ResponseEntity<Map<String, Object>> buscarEconomico(){
        return productoService.obtenerProductoMasBarato()
                .map(dto -> {
                    EntityModel<ProductoResponseDTO> resource = EntityModel.of(dto,
                            linkTo(methodOn(ProductoController.class).buscarEconomico()).withSelfRel(),
                            linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("productos")
                    );

                    Map<String, Object> respuesta = new LinkedHashMap<>();
                    respuesta.put("mensaje", "Se localizó el producto con el precio más bajo en el inventario.");
                    respuesta.put("resultado", resource);
                    return ResponseEntity.ok(respuesta);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener sugerencia aleatoria", description = "Devuelve una lista aleatoria de productos como sugerencia")
    @GetMapping("/sugerencia")
    public ResponseEntity<Map<String, Object>> buscarSugerencia(){
        List<ProductoResponseDTO> productos = productoService.buscarSugerenciaAleatorio();
        return procesarBusquedaColeccion(productos, "Lista de sugerencias aleatorias generada correctamente.",
                linkTo(methodOn(ProductoController.class).buscarSugerencia()).withSelfRel());
    }

    /**
     * Método auxiliar privado reutilizado para unificar las respuestas de colecciones HATEOAS.
     */
    private ResponseEntity<Map<String, Object>> procesarBusquedaColeccion(
            List<ProductoResponseDTO> productos, String mensaje, org.springframework.hateoas.Link selfLink) {

        List<EntityModel<ProductoResponseDTO>> list = productos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProductoController.class).obtenerPorId(dto.getIdProducto())).withSelfRel(),
                        linkTo(methodOn(ProductoController.class).obtenerTodo()).withRel("todos-productos")
                ))
                .toList();

        CollectionModel<EntityModel<ProductoResponseDTO>> collection = CollectionModel.of(list, selfLink);

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("coincidencias", productos.size());
        respuesta.put("resultado", collection);

        return ResponseEntity.ok(respuesta);
    }
}


