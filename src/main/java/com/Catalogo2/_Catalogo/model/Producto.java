package com.Catalogo2._Catalogo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "producto")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(nullable = false,length = 100)
    private String nombre;

    @Column(nullable = false,length = 100)
    private String categoria;

    @Column(name = "Precio_unitario",nullable = false)
    private Integer precioUnitario;

    @Column(nullable = false,length = 100)
    private String fabricante;


}
