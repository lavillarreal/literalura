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
        libroRepository.save(libro);
    }

    public void saveAutor(Autor autor) {
        autorRepository.save(autor);
    }

    public void saveAutoria(AutoriaLibro autoria){
        autoriaRepository.save(autoria);
    }

    // Otros métodos de lógica
}