package com.alura.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.literalura.entity.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

	public List<Libro> findByNumeroDescargasGreaterThan(int numeroDescargas);
	
	public List<Libro> findByNumeroDescargasGreaterThanEqual(int numeroDescargas);

}