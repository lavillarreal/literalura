package com.alura.literalura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.literalura.entity.*;


public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreAndApellido(String nombre, String apellido);
}
