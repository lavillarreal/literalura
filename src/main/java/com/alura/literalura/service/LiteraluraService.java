package com.alura.literalura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.literalura.entity.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.AutoriaLibroRepository;
import com.alura.literalura.repository.LibroRepository;

@Service
public class LiteraluraService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutoriaLibroRepository autoriaRepository;

    public void saveLibro(Libro libro) {
        this.libroRepository.save(libro);
    }

    public void saveAutor(Autor autor) {
        this.autorRepository.save(autor);
    }

    public void saveAutoria(AutoriaLibro autoria){
        this.autoriaRepository.save(autoria);
    }

    public LibroRepository getLibroRepository(){
        return this.libroRepository;
    }

    public AutoriaLibroRepository getAutoriaRepository(){
        return this.autoriaRepository;
    }

    public AutorRepository getAutorRepository(){
        return this.autorRepository;
    }

    public void setAuditoriaRepository(AutoriaLibroRepository newAutoriaRepository){
        this.autoriaRepository = newAutoriaRepository;
    }

    public void setAutorRepository(AutorRepository newAutorRepository){
        this.autorRepository = newAutorRepository;
    }

    public void setLibroRepository(LibroRepository newLibroRepository){
        this.libroRepository = newLibroRepository;
    }

    // Otros métodos de lógica
}