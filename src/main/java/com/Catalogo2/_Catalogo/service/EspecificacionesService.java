package com.Catalogo2._Catalogo.service;

import com.Catalogo2._Catalogo.model.Especificaciones;
import com.Catalogo2._Catalogo.model.Producto;
import com.Catalogo2._Catalogo.repository.EspecificacionesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspecificacionesService {

    private final EspecificacionesRepository especificacionesRepository;

    public List<Especificaciones> obtenerTodo(){
        return especificacionesRepository.findAll();
    }

    public Optional<Especificaciones> obtenerPorID(Long id){
        return especificacionesRepository.findById(id);
    }

    public Especificaciones especificacionesService(Especificaciones especificaciones){
        return especificacionesRepository.save(especificaciones);
    }

    public void eliminar(Long id){
        especificacionesRepository.deleteById(id);
    }

}
