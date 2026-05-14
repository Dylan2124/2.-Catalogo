package com.Catalogo2._Catalogo.repository;

import com.Catalogo2._Catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByidProducto(Long idProducto);

    // filtrar marcas de hardware (Nvidia, ASUS, etc)
    List<Producto> findByFabricanteIgnoreCase(String fabricante);

    //Para Hardware por categoria (CPUs, GPUs etc)
    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria")
    List<Producto> buscarPorCategoria(@Param("categoria") String categoria);

    @Query("SELECT p FROM Producto p WHERE p.precioUnitario <= :max ORDER BY p.precioUnitario ASC")
    List<Producto> findHardwareEconomico(@Param("max") Integer max);

    // filtro de busqueda por fabricante y precio.
    @Query(value = "SELECT * FROM producto ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Producto> obtenerSugerenciaAleatoria();
}
