package com.Catalogo2._Catalogo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecificacion;

    @Column(nullable = false,length = 100)
    private String atributo;

    @Column(nullable = false,length = 255)
    private String valor;
}


