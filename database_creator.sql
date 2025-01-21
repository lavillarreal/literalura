-- Crear usuario de DB literalura
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

-- Crear base de datos Literalura
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
    birth INT NOT NULL DEFAULT 0,
    death INT NOT NULL DEFAULT 0
);

-- Tabla de libros
CREATE TABLE libros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    idioma VARCHAR(2) NOT NULL,
    numeroDescargas INT DEFAULT 0,
	idRemoto INT NOT NULL
);

-- Tabla intermedia para la relación libros y autores
CREATE TABLE autoria_libro (
	id SERIAL PRIMARY KEY,
    libroId INT NOT NULL,
    autorId INT NOT NULL
);

GRANT ALL PRIVILEGES ON DATABASE literalura TO literalura;

GRANT ALL PRIVILEGES ON TABLE autores TO literalura;

GRANT ALL PRIVILEGES ON TABLE libros TO literalura;

GRANT ALL PRIVILEGES ON TABLE autoria_libro TO literalura;

ALTER TABLE autoria_libro OWNER TO literalura;

ALTER TABLE autores OWNER TO literalura;

ALTER TABLE libros OWNER TO literalura;
