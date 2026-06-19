package com.Catalogo2._Catalogo.service;

import com.Catalogo2._Catalogo.dto.ProductoRequestDTO;
import com.Catalogo2._Catalogo.model.Especificacion;
import com.Catalogo2._Catalogo.model.Producto;
import com.Catalogo2._Catalogo.repository.EspecificacionesRepository;
import com.Catalogo2._Catalogo.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private EspecificacionesRepository especificacionesRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto ejemploProducto;
    private Especificacion ejemploEspecificacion;

    @BeforeEach
    void setUp(){
        ejemploEspecificacion = new Especificacion();
        ejemploEspecificacion.setIdEspecificacion(10L);
        ejemploEspecificacion.setAtributo("Switch: Red");

        ejemploProducto = new Producto(
                1L,
                "Teclado Mecánico",
                "Periféricos",
                new Integer("40.000"),
                "KeyBrand",
                List.of(ejemploEspecificacion)
        );
        // relacion bidireccional (si se usa en lógica)
        ejemploEspecificacion.setProducto(ejemploProducto);
    }

    @Test
    @DisplayName("Caso de éxito: obtener producto por ID existente")
    void obtenerPorId_existente_debeRetornarDTO() {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.of(ejemploProducto));

        // Act
        Optional<?> resultado = productoService.obtenerPorId(1L);

        // Assert
        assertTrue(resultado.isPresent(), "Se esperaba que el Optional estuviera presente");
        var dto = resultado.get();
        // Verificar propiedades básicas mediante toString o reflexión mínima
        assertNotNull(dto);
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Caso de falla: obtener producto por ID inexistente debe retornar Optional vacío")
    void obtenerPorId_inexistente_debeRetornarVacio() {
        // Arrange
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<?> resultado = productoService.obtenerPorId(999L);

        // Assert
        assertTrue(resultado.isEmpty(), "Se esperaba Optional vacío cuando no existe el producto");
        verify(productoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Caso de guardado: debe llamar a save del repositorio exactamente una vez y devolver DTO")
    void guardar_debeLlamarSaveUnaVez() {
        // Arrange
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Teclado Mecánico");
        request.setCategoria("Periféricos");
        request.setPrecioUnitario(new Integer("40.000"));
        request.setFabricante("KeyBrand");
        request.setEspecificacionId(10L);

        when(especificacionesRepository.findById(10L)).thenReturn(Optional.of(ejemploEspecificacion));

        // El repositorio guarda y devuelve la entidad con ID asignado
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            // Simular que la base de datos asigna el ID 1
            p.setIdProducto(1L);
            return p;
        });

        // Act
        var respuesta = productoService.guardar(request);

        // Assert
        assertNotNull(respuesta, "El DTO de respuesta no debe ser nulo");
        assertEquals("Teclado Mecánico", respuesta.getNombre());

        // Verificar que save fue llamado exactamente una vez
        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository, times(1)).save(captor.capture());
        Producto guardado = captor.getValue();
        assertEquals("Teclado Mecánico", guardado.getNombre());
        verify(especificacionesRepository, times(1)).findById(10L);
    }
}

