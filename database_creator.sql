-- Crear base de datos Literalura
CREATE ROLE literalura WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	NOBYPASSRLS
	CONNECTION LIMIT 500
	PASSWORD 'xxxxxx';


CREATE DATABASE literalura
    WITH
    OWNER = literalura
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


-- Tabla de autores
CREATE TABLE autores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    birth INT NOT NULL,
    death INT NOT NULL DEFAULT 0
);

-- Tabla de libros
CREATE TABLE libros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    idioma CHAR(2) NOT NULL,
    numero_descargas INT DEFAULT 0
);

-- Tabla intermedia para la relación muchos a muchos entre libros y autores
CREATE TABLE libros_autores (
    libro_id INT NOT NULL,
    autor_id INT NOT NULL,
    PRIMARY KEY (libro_id, autor_id),
    FOREIGN KEY (libro_id) REFERENCES libros(id) ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES autores(id) ON DELETE CASCADE
);
