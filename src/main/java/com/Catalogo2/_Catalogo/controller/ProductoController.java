package com.Catalogo2._Catalogo.controller;

import com.Catalogo2._Catalogo.dto.ProductoRequestDTO;
import com.Catalogo2._Catalogo.dto.ProductoResponseDTO;
import com.Catalogo2._Catalogo.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodo(){
        return ResponseEntity.ok(productoService.obtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id){
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> guardarProducto(@Valid @RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO nuevoProducto = productoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id,@Valid @RequestBody ProductoRequestDTO dto){
        return productoService.actualizar(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idProducto){
        if(productoService.obtenerPorId(idProducto).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        productoService.eliminar(idProducto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@RequestParam("nombre") String nombre){
        List<ProductoResponseDTO> producto = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/fabricante")
    public ResponseEntity<List<ProductoResponseDTO>> buscarFabricante(@RequestParam String fabricante){
        List<ProductoResponseDTO> fabricanteBuscado = productoService.buscarPorFabricante(fabricante);
        return ResponseEntity.ok(fabricanteBuscado);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<ProductoResponseDTO>> buscarCategori(@RequestParam String categori){
        List<ProductoResponseDTO> categoriaBuscado = productoService.buscarPorCategoria(categori);
        return ResponseEntity.ok(categoriaBuscado);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<ProductoResponseDTO>> BuscarHardwareEconomico(@RequestParam Integer precio){
        List<ProductoResponseDTO> productos = productoService.HardwareEconomico(precio);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/sugerencia")
    public ResponseEntity<List<ProductoResponseDTO>> buscarSugerencia(){
        return ResponseEntity.ok(productoService.buscarSugerenciaAleatorio());
    }


}
