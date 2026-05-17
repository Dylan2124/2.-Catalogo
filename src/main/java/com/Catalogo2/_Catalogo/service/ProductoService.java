package com.Catalogo2._Catalogo.service;

import com.Catalogo2._Catalogo.dto.ProductoRequestDTO;
import com.Catalogo2._Catalogo.dto.ProductoResponseDTO;
import com.Catalogo2._Catalogo.model.Especificacion;
import com.Catalogo2._Catalogo.model.Producto;
import com.Catalogo2._Catalogo.repository.EspecificacionesRepository;
import com.Catalogo2._Catalogo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    private final EspecificacionesRepository especificacionesRepository;

    private ProductoResponseDTO mapToDTO(Producto producto){
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getPrecioUnitario(),
                producto.getFabricante(),
                producto.getCategoria()

        );
    }

    // Para obtener todos
    public List<ProductoResponseDTO> obtenerTodo(){
        return productoRepository.findAll()
                 .stream()
                 .map(this::mapToDTO)
                 .collect(Collectors.toList());
    }

    public Optional<ProductoResponseDTO> obtenerPorId(Long idP){
        return productoRepository.findById(idP)
                .map(this::mapToDTO);
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO dto){
        Especificacion especificacion = especificacionesRepository
                .findById(dto.getEspecificacionId())
                .orElseThrow(() -> {
                    log.error("Error al registrar: Especificaciones con ID {} no existe",dto.getEspecificacionId());
                    return new IllegalArgumentException("Las especificaciones con ID: "+dto.getEspecificacionId()+ "no fue encontrado");
                        });
        Producto producto = new Producto(
                null,
                dto.getNombre(),
                dto.getCategoria(),
                dto.getPrecioUnitario(),
                dto.getFabricante(),
                Collections.singletonList(especificacion)

        );
        Producto guardado = productoRepository.save(producto);
        return mapToDTO(guardado);
    }

    public Optional<ProductoResponseDTO> actualizar(Long id, ProductoRequestDTO dto){
        return productoRepository.findById(id).map(existe ->{
            Especificacion especificacion = especificacionesRepository
                    .findById(dto.getEspecificacionId())
                    .orElseThrow(() -> {
                        log.error("Error al actualizar: Especificación con ID {} no existe", dto.getEspecificacionId());
                        return new IllegalArgumentException("Especificación no encontrada: " + dto.getEspecificacionId());
                    });

            existe.setNombre(dto.getNombre());
            existe.setCategoria(dto.getCategoria());
            existe.setPrecioUnitario(dto.getPrecioUnitario());
            existe.setFabricante(dto.getFabricante());
            especificacion.setProducto(existe);
            existe.setEspecificaciones(new ArrayList<>(List.of(especificacion)));
            return mapToDTO(productoRepository.save(existe));
        });
        }

    public void eliminar(Long id){
        productoRepository.deleteById(id);
    }

    // Metodo del repository
    public List<ProductoResponseDTO> buscarPorNombre(String nombreProducto){
        return productoRepository.findByNombreContainingIgnoreCase(nombreProducto)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    public List<ProductoResponseDTO> buscarPorFabricante(String fabricante){
        return productoRepository.findByFabricanteIgnoreCase(fabricante)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarPorCategoria(String categoria){
        return productoRepository.buscarPorCategoria(categoria)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ProductoResponseDTO> obtenerProductoMasBarato() {
        return productoRepository.findFirstByOrderByPrecioUnitarioAsc()
                .map(this::mapToDTO);

    }

    public List<ProductoResponseDTO> buscarSugerenciaAleatorio(){
        List<Producto> productoAleatorio = productoRepository.obtenerSugerenciaAleatoria();
        return productoAleatorio
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
