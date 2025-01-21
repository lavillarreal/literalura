
# Literalura - Aplicación de Gestión de Libros y Autores

## Descripción
Este proyecto es una aplicación en Java que permite gestionar libros y autores, utilizando Spring Boot y JPA. La aplicación interactúa con una base de datos para almacenar información sobre libros y autores, y también realiza consultas a una API externa (Gutendex) para obtener libros según el título. Permite realizar diversas operaciones como buscar libros por título, listar libros registrados, listar autores registrados, y más.

## Requisitos
- JDK 11 o superior.
- Maven para la construcción del proyecto.
- Base de datos configurada (por defecto, se usa JPA con una base de datos relacional).
- Conexión a Internet para acceder a la API externa de Gutendex.

## Dependencias
- Spring Boot
- JPA (Java Persistence API)
- Gson para parseo de JSON
- SSL para conexiones seguras

## Instalación
1. Clona este repositorio en tu máquina local.
2. Abre una terminal en el directorio raíz del proyecto.
3. Ejecuta el siguiente comando para compilar el proyecto:

   ```bash
   mvn clean install
   ```

4. Para ejecutar la aplicación, usa el siguiente comando:

   ```bash
   mvn spring-boot:run
   ```

## Funcionalidades
La aplicación ofrece un menú interactivo para el usuario, con las siguientes opciones:

1. **Buscar por título**: Permite buscar libros por título en la API de Gutendex.
2. **Listar libros registrados**: Muestra todos los libros almacenados en la base de datos.
3. **Listar autores registrados**: Muestra todos los autores almacenados en la base de datos.
4. **Listar autores vivos en un año específico**: Permite consultar qué autores estaban vivos en un año determinado.
5. **Listar libros por idioma**: Muestra los libros almacenados en la base de datos de un idioma específico.
6. **Salir**: Cierra la aplicación.

## Estructura del Proyecto
- **`com.alura.literalura`**: Paquete principal de la aplicación que contiene las clases de servicio, repositorios y la configuración de Spring Boot.
- **`entity`**: Contiene las clases que representan las entidades `Libro`, `Autor` y `AutoriaLibro` que se almacenan en la base de datos.
- **`repository`**: Contiene los repositorios JPA para acceder a las entidades `Libro`, `Autor` y `AutoriaLibro`.
- **`service`**: Contiene la clase `LiteraluraService`, que maneja la lógica de negocio de la aplicación.

# Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

```
LITERALURA/
│
├── .mvn/                    # Archivos de configuración de Maven.
├── .vscode/                 # Archivos de configuración de Visual Studio Code.
├── src/                     # Código fuente del proyecto.
│   ├── main/                # Código principal.
│   │   ├── java/            # Código Java.
│   │   │   └── com/alura/literalura/
│   │   │       ├── controller/       # Controladores que manejan la lógica de la aplicación.
│   │   │       │   └── Autor.java    # Clase que representa un autor.
│   │   │       ├── entity/           # Entidades de la base de datos.
│   │   │       │   ├── Autor.java    # Representación del autor en la base de datos.
│   │   │       │   ├── AutorLibro.java # Relación entre autor y libro.
│   │   │       │   └── Libro.java    # Representación del libro.
│   │   │       ├── repository/      # Repositorios que gestionan la persistencia de datos.
│   │   │       │   ├── AutorLibroRepository.java # Interfaz para gestionar AutorLibro.
│   │   │       │   ├── AutorRepository.java      # Interfaz para gestionar autores.
│   │   │       │   └── LibroRepository.java      # Interfaz para gestionar libros.
│   │   │       ├── service/          # Servicios que contienen la lógica de negocio.
│   │   │       │   └── LiteraluraApplication.java # Clase principal que ejecuta la aplicación.
│   │   │       └── resources/        # Archivos de configuración y recursos.
│   │   │           ├── application.properties  # Configuración de la aplicación.
│   │   │           └── ca.crt         # Certificado para conexión segura (si es necesario).
│   └── test/                  # Pruebas unitarias del proyecto.
│       └── java/               # Código de pruebas en Java.
│           └── com/alura/literalura/
│               └── LiteraluraApplicationTests.java  # Pruebas para la aplicación principal.
│
├── target/                    # Carpeta generada por Maven para archivos compilados y empaquetados.
├── .gitattributes             # Archivo de configuración de atributos de Git.
├── .gitignore                 # Archivos y carpetas a ser ignorados por Git.
├── commands.txt               # Archivo de comandos, posiblemente relacionado con la configuración del proyecto.
├── database_creator.sql       # Script para la creación de la base de datos.
├── LICENSE                    # Licencia del proyecto.
├── pom.xml                    # Archivo de configuración de Maven.
├── mnvw/                      # Script de Maven para Linux/Mac.
├── mnvw.cmd                   # Script de Maven para Windows.
├── README.md                  # Archivo de documentación principal.
└── response-example.json      # Ejemplo de respuesta en formato JSON.
```

**Breve Descripción:**

1. **.mvn**: Contiene archivos de configuración de Maven para gestionar la construcción y el ciclo de vida del proyecto.
2. **.vscode**: Contiene configuraciones específicas para el editor Visual Studio Code.
3. **src/main/java/com/alura/literalura**: Contiene el código fuente de la aplicación en Java.
   - **controller**: Contiene las clases que gestionan la lógica de la aplicación, como `Autor.java` que define un autor.
   - **entity**: Define las entidades que representan las tablas de la base de datos, como `Autor.java`, `AutorLibro.java`, y `Libro.java`.
   - **repository**: Interfaz que facilita el acceso y manipulación de los datos de las entidades mediante operaciones CRUD.
   - **service**: Contiene la lógica de negocio y la clase principal `LiteraluraApplication.java`.
   - **resources**: Contiene archivos de configuración y recursos, como el archivo de configuración de la aplicación y el certificado SSL.
4. **src/test/java**: Contiene las pruebas unitarias para asegurar el funcionamiento del proyecto.
5. **target**: Directorio generado por Maven que contiene los archivos compilados y empaquetados.
6. **.gitignore**: Define qué archivos deben ser ignorados por Git durante el control de versiones.
7. **database_creator.sql**: Script SQL que se utiliza para crear la base de datos.
8. **LICENSE**: Contiene la licencia bajo la cual se distribuye el proyecto.
9. **pom.xml**: Archivo de configuración de Maven que especifica las dependencias y plugins del proyecto.
10. **mnvw/mnvw.cmd**: Scripts de Maven para ejecutar el proyecto en sistemas Unix y Windows, respectivamente.
11. **README.md**: Archivo que contiene información del proyecto, cómo usarlo, y detalles importantes para los desarrolladores.
12. **response-example.json**: Un archivo de ejemplo de respuesta en formato JSON, posiblemente para la API del proyecto.


## Contribuciones
Las contribuciones son bienvenidas. Si deseas contribuir al proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -am 'Añadida nueva funcionalidad'`).
4. Envía un pull request.

## Licencia
Este proyecto está licenciado bajo la Licencia MIT - consulta el archivo [LICENSE](LICENSE) para más detalles.