package com.alura.literalura.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name="idioma", nullable = false, length = 2)
    private String idioma;

    @Column(name="numero_descargas", nullable = false)
    private int numeroDescargas = 0;

    @Column(name = "idRemoto", nullable = false)
    private int idRemoto;

    @ElementCollection
    private List<Autor> autores;

    public Libro() {}

    // Constructor con parámetros (opcional)
    public Libro(String titulo, String idioma, int numeroDescargas, List<Autor> autores) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autores = autores;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public int getIdRemoto(){
        return idRemoto;
    }

    public void setIdRemoto(int newIdRemoto){
        idRemoto = newIdRemoto;
    }
}
