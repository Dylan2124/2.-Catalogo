package com.Catalogo2._Catalogo.service;

import com.Catalogo2._Catalogo.model.Especificacion;
import com.Catalogo2._Catalogo.repository.EspecificacionesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspecificacionesService {

    private final EspecificacionesRepository especificacionesRepository;

    public List<Especificacion> obtenerTodo(){

        return especificacionesRepository.findAll();
    }

    public Optional<Especificacion> obtenerPorID(Long id){

        return especificacionesRepository.findById(id);
    }

    public Especificacion especificacionesService(Especificacion especificaciones){
        return especificacionesRepository.save(especificaciones);
    }

    public void eliminar(Long id){
        especificacionesRepository.deleteById(id);
    }

}
