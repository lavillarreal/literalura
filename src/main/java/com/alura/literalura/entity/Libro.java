package com.alura.literalura.entity;

import java.util.ArrayList;
import java.util.List;

import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.AutoriaLibroRepository;
import com.alura.literalura.service.LiteraluraService;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name="idioma", nullable = false, length = 2)
    private String idioma;

    @Column(name="numerodescargas", nullable = false)
    private int numeroDescargas = 0;

    @Column(name = "idremoto", nullable = false)
    private int idRemoto = 0;

    @Transient
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    private List<Autor> getAutorsFromDB(AutoriaLibroRepository repository, AutorRepository autorRepository){
        List<AutoriaLibro> autorias = repository.findByLibroId(this.getId());
        if(!autorias.isEmpty()){
            List<Autor> autores = new ArrayList<>();
            for (AutoriaLibro autoria : autorias){
                autores.add(autorRepository.findById(Long.parseLong(Integer.toString(autoria.getAutorId()))).isPresent() ? autorRepository.findById(Long.parseLong(Integer.toString(autoria.getAutorId()))).get() : new Autor() );
            }
            return autores;
        }
        return null;
        
    }

    public List<Autor> getAuthors(LiteraluraService databaseContext){
        return this.getAutorsFromDB(databaseContext.getAutoriaRepository(), databaseContext.getAutorRepository());
    }

    @Override
    public String toString() {
        return "\n\nLibro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                ", idRemoto=" + idRemoto +
                '}';
    }
}
