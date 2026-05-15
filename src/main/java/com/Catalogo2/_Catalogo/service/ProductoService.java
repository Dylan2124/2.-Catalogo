package com.Catalogo2._Catalogo.service;

import com.Catalogo2._Catalogo.dto.ProductoRequestDTO;
import com.Catalogo2._Catalogo.dto.ProductoResponseDTO;
import com.Catalogo2._Catalogo.model.Especificaciones;
import com.Catalogo2._Catalogo.model.Producto;
import com.Catalogo2._Catalogo.repository.EspecificacionesRepository;
import com.Catalogo2._Catalogo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
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
        log.info("Iniciando registro de nuevo producto: {}");
        Especificaciones especificaciones = especificacionesRepository
                .findById(dto.getEspecificacionId())
                .orElseThrow(() -> new RuntimeException(
                        "Las especificaciones con ID: " + dto.getEspecificacionId()+ " no fue encontrado "));

        Producto producto = new Producto(
                null,
                dto.getNombre(),
                dto.getCategoria(),
                dto.getPrecioUnitario(),
                dto.getFabricante(),
                especificaciones

        );
        return mapToDTO(productoRepository.save(producto));

    }

    public Optional<ProductoResponseDTO> actualizar(Long id, ProductoRequestDTO dto){
        return productoRepository.findById(id).map(existe ->{
            Especificaciones especificaciones = especificacionesRepository
                    .findById(dto.getEspecificacionId())
                    .orElseThrow(() -> new RuntimeException(
                            "Especificaciones no encontra" + dto.getEspecificacionId()));

            existe.setNombre(dto.getNombre());
            existe.setCategoria(dto.getCategoria());
            existe.setPrecioUnitario(dto.getPrecioUnitario());
            existe.setFabricante(dto.getFabricante());
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

    public List<ProductoResponseDTO> HardwareEconomico(Integer precio){
        return productoRepository.findHardwareEconomico(precio)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarSugerenciaAleatorio(){
        List<Producto> productoAleatorio = productoRepository.obtenerSugerenciaAleatoria();
        return productoAleatorio
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }




}
