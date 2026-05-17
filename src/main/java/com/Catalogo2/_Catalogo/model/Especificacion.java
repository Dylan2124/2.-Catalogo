package com.Catalogo2._Catalogo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "especificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especificacion")
    private Long idEspecificacion;

    @Column(nullable = false,length = 255)
    private String atributo;

    @Column(nullable = false,length = 255)
    private String valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto",nullable = true)
    @ToString.Exclude
    @JsonIgnoreProperties("especificacion")
    private Producto producto;

}


