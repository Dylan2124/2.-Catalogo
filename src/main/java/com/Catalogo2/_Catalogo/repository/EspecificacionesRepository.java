package com.Catalogo2._Catalogo.repository;

import com.Catalogo2._Catalogo.model.Especificacion;
import com.Catalogo2._Catalogo.model.Especificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecificacionesRepository extends JpaRepository<Especificacion, Long> {

    List<Especificacion> findByProducto_IdProducto(Long id);

    List<Especificacion> findByAtributoIgnoreCase(String atributo);

}
