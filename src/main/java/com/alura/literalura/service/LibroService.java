package com.alura.literalura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.literalura.entity.Autor;
import com.alura.literalura.entity.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void saveLibros(List<Libro> libros) {
        for (Libro libro : libros) {
            // Persistir autores primero para evitar duplicados
            for (Autor autor : libro.getAutores()) {
                Autor existingAutor = autorRepository.findByNombreAndApellido(
                    autor.getNombre(), autor.getApellido()
                ).orElse(null);

                if (existingAutor != null) {
                    autor.setId(existingAutor.getId());
                } else {
                    autorRepository.save(autor);
                }
            }
            // Persistir el libro
            libroRepository.save(libro);
        }
    }
     // Crear o actualizar un libro
     public void saveLibro(Libro libro) {
        libroRepository.save(libro);
    }

    // Obtener todos los libros
    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    // Obtener un libro por su id
    public Optional<Libro> getLibroById(Long id) {
        return libroRepository.findById(id);
    }

    // Eliminar un libro por su id
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }
}
