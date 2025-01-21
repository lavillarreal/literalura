 package com.alura.literalura.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "autoria_libro")
public class AutoriaLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="libroid", nullable = false)
    private int libroId;

    @Column(name = "autorid", nullable = false)
    private int autorId;

    public AutoriaLibro(){}

    // Constructor con parámetros (opcional)
    public AutoriaLibro(int newAutorId, int newLibroId) {
        this.autorId = newAutorId;
        this.libroId = newLibroId;
    }

    // Getters y Setters

    public void setAutorId(int newAutorId){
        this.autorId = newAutorId;
    }

    public void setLibroId(int newLibroId){
        this.libroId = newLibroId;
    }

    public int getLibroId(){
        return libroId;
    }

    public int getAutorId(){
        return autorId;
    }

}
