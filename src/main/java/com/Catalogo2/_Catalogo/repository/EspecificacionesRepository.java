package com.Catalogo2._Catalogo.repository;

import com.Catalogo2._Catalogo.model.Especificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecificacionesRepository extends JpaRepository<Especificaciones, Long> {

}
