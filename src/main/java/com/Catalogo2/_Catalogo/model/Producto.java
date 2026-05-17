package com.Catalogo2._Catalogo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "producto", schema = "catalogo_db")
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

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Especificacion> especificaciones;


}
