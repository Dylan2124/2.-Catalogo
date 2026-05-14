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
        Especificaciones especificaciones = especificacionesRepository
                .findById(dto.get)
                .orElseThrow() -> new RuntimeException("La categoria con ID: " + dto.ge

        )
    }


}
