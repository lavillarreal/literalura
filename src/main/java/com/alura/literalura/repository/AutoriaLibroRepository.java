package com.alura.literalura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.literalura.entity.*;


public interface AutoriaLibroRepository extends JpaRepository<AutoriaLibro, Long> {
    Optional<AutoriaLibro> findByLibroId(int libroId);
}
