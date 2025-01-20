 package com.alura.literalura.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class AutoriaLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="libro_id", nullable = false)
    private int libroId;

    @Column(name = "autor_id", nullable = false)
    private Long autorId;

    public AutoriaLibro(){}

    // Constructor con parámetros (opcional)
    public AutoriaLibro(Long newAutorId, int newLibroId) {
        this.autorId = newAutorId;
        this.libroId = newLibroId;
    }

    // Getters y Setters

    public void setAutorId(Long newAutorId){
        this.autorId = newAutorId;
    }

    public void setLibroId(int newLibroId){
        this.libroId = newLibroId;
    }

    public int getLibroId(){
        return libroId;
    }

    public Long getAutorId(){
        return autorId;
    }

}
