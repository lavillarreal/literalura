package com.alura.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.literalura.entity.*;

public interface AutoriaLibroRepository extends JpaRepository<AutoriaLibro, Long> {
    List<AutoriaLibro> findByLibroId(int libroId);
}
