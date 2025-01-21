package com.alura.literalura;

import com.alura.literalura.entity.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.AutoriaLibroRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.LiteraluraService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import javax.net.ssl.*;
import java.security.cert.Certificate;

import com.google.gson.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = "com.alura.literalura.*")
@EntityScan(basePackages = {"com.alura.literalura.*"})
@EnableJpaRepositories(basePackages = "com.alura.literalura.*")
public class LiteraluraApplication {

	@Autowired
    private static LiteraluraService databaseContext;

	private static BufferedReader stdin_reader;
	
	public static void main(String[] args) throws IOException {
	
		ApplicationContext context = SpringApplication.run(LiteraluraApplication.class, args);
		stdin_reader = new BufferedReader(new InputStreamReader(System.in));

		databaseContext = new LiteraluraService();

		databaseContext.setLibroRepository(context.getBean(LibroRepository.class));
		databaseContext.setAutorRepository(context.getBean(AutorRepository.class));
		databaseContext.setAuditoriaRepository(context.getBean(AutoriaLibroRepository.class));

		BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.print("\n\n\n1) Buscar por titulo\n2) Listar libros registrados\n3) Listar autores registrados\n" +
			"4) Listar autores vivos en determinado año\n5) Listar libros por idioma\n6) Salir\n -- INGRESE OPCION --");

			String input = stdin_reader.readLine();

			switch (input){
				case "1" -> BuscarPorTitulo();
				case "2" -> ListarLibrosRegistrados();
				case "3" -> ListarAutoresRegistrados();
				case "4" -> ListarAutoresVivosEnUnAño();
				case "5" -> ListarLibrosPorIdioma();
				case "6" -> System.exit(0);
 			}
		}
	}

	private static void BuscarPorTitulo() {
		String apiUrl = "https://gutendex.com/books?search=";

		System.out.print("Ingrese título a buscar: ");
		try {
			BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));
			String titulo = stdin_reader.readLine(); // Puede lanzar IOException

			try {
				// Crear conexión HTTP
				int responseCode = 0;
				URL url = new URL(apiUrl + titulo);
				HttpURLConnection connection;

				try{
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setRequestProperty("Accept", "application/json");
					responseCode = connection.getResponseCode();
				}
				catch (javax.net.ssl.SSLHandshakeException ex){
					String caCertPath = "src/main/resources/ca.crt";
        
					// Configurar el TrustManager con la CA
					SSLContext sslContext = configureTrustManager(caCertPath);
					HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

					connection = (HttpURLConnection) url.openConnection();

					// Configuración adicional de la conexión
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(30000);
					connection.setReadTimeout(30000);

					responseCode = connection.getResponseCode();
					System.out.println("Response Code: " + responseCode);
				}
			
				if (responseCode == HttpURLConnection.HTTP_OK) {
					// Leer la respuesta
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					reader.close();

					// Extraer y procesar los datos manualmente
					String jsonResponse = response.toString();

					List<Libro> libros = parseLibrosFromJsonToDB(jsonResponse);

					for (Libro libro : libros) {
						System.out.println("Título: " + libro.getTitulo());
						System.out.println("Idioma: " + libro.getIdioma());
						System.out.println("Descargas: " + libro.getNumeroDescargas());
						System.out.println("Autores:");
						for (Autor autor : libro.getAutores()) {
							System.out.println("  - " + autor.getNombre() + " (" + autor.getBirth() + "-" + autor.getDeath() + ")");
						}
						System.out.println("--------------------------");
					}

					//System.out.println(jsonResponse);

				} else {
					System.out.println("Error en la solicitud: " + responseCode);
				}
				connection.disconnect();
			
			} 

			catch (Exception e) {
				System.out.println("Error al realizar la solicitud HTTP: " + e.getMessage() + " " + e.getClass());
			}

		} catch (IOException e) {
			System.out.println("Error al leer el título: " + e.getMessage());
		}
	}

	public static List<Libro> parseLibrosFromJsonToDB(String jsonResponse) throws IOException {
        List<Libro> libros = new ArrayList<>();

        // Deserializar JSON
        JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");
        System.out.print("Resultados parseado");

        for (JsonElement element : results) {
            JsonObject bookJson = element.getAsJsonObject();
            Libro libro = new Libro();

            // Crear Libro
            libro.setTitulo(bookJson.get("title").getAsString());
			System.out.print(bookJson.get("title").getAsString());

            libro.setIdioma(bookJson.getAsJsonArray("languages").get(0).getAsString());
            libro.setNumeroDescargas(bookJson.get("download_count").getAsInt());
			libro.setIdRemoto(bookJson.has("id") ? bookJson.get("id").getAsInt() : 0);


			//guardarLibro
			try{
				databaseContext.saveLibro(libro);
			}
			catch (org.springframework.dao.DataIntegrityViolationException e){
				System.out.println(e.getMessage());
			}
		
			
            // Procesar autores
            JsonArray authorsJson = bookJson.getAsJsonArray("authors");
            List<Autor> autores = new ArrayList<>();
            String[] authorName;

            for (JsonElement authorElement : authorsJson) {
                JsonObject authorJson = authorElement.getAsJsonObject();
                Autor autor = new Autor();
                authorName = authorJson.get("name").getAsString().split(",");

                autor.setNombre(authorName[1]);
                autor.setApellido(authorName[0]);

                try {
                    autor.setBirth(authorJson.has("birth_year") ? authorJson.get("birth_year").getAsInt() : 0);
                } catch (java.lang.UnsupportedOperationException e) {
                    autor.setBirth(0);
                    System.out.print("Cant get birth date for author: " + autor.getNombre());
                } catch (Exception e) {
                    System.out.print(e.getClass());
                }

                try {
                    autor.setDeath(authorJson.has("death_year") ? authorJson.get("death_year").getAsInt() : 0);
                } catch (java.lang.UnsupportedOperationException e) {
                    autor.setDeath(0);
                    System.out.print("Cant get death date for author: " + autor.getNombre());
                } catch (Exception e) {
                    System.out.println(e.getClass());
                }
				autores.add(autor);
				libro.setAutores(autores);
            }

            
			for (Autor autor : libro.getAutores()){
				try{
					Autor existingAutor = databaseContext.getAutorRepository().findByNombreAndApellido(autor.getNombre(), autor.getApellido()).orElse(null);
					if (existingAutor == null){
						databaseContext.saveAutor(autor);
					}
					existingAutor = databaseContext.getAutorRepository().findByNombreAndApellido(autor.getNombre(), autor.getApellido()).orElse(null);
					if (existingAutor != null){
						AutoriaLibro autoria = new AutoriaLibro();
						autoria.setAutorId(existingAutor.getId());
						autoria.setLibroId(libro.getIdRemoto());
						databaseContext.saveAutoria(autoria);
					}
					else{
						System.out.print("ERROR EN AUTORIA");
					}
				}
				catch(Exception e){
					System.out.println("Error en la lógica de grabado " + e.getClass());
				}
			}
			
        }

        return libros;
    }

	private static void ListarLibrosRegistrados(){

		List<Libro> librosEnDB = databaseContext.getLibroRepository().findAll();
		if (librosEnDB.isEmpty()){
			System.out.print("No hay libros almacenados");
		}
		else{
			for (Libro libro : librosEnDB){
				System.out.print(libro.toString());
			}	
		}
	}

	private static void ListarAutoresRegistrados(){

		List<Autor> autoresEnDB = databaseContext.getAutorRepository().findAll();
		if (autoresEnDB.isEmpty()){
			System.out.print("No hay Autores almacenados");
		}
		else{
			for (Autor autor : autoresEnDB){
				System.out.print(autor.toString());
			}	
		}

	}

	private static void ListarAutoresVivosEnUnAño(){

		int parametro;

		while (true){
			try{
				System.out.println("Ingrese el año a revisar: ");
				parametro = Integer.parseInt(stdin_reader.readLine());
				if (parametro > 0){
					break;
				}
				else{
					System.out.println(Integer.toString(parametro) + "NO es un año válido");
				}
			}
			catch(Exception e){
				System.out.println("Entrada inválida");
			}
		}

		List<Autor> autoresEnDB = databaseContext.getAutorRepository().findAll();

		if (autoresEnDB.isEmpty()){
			System.out.print("No hay Autores vivos en el año: " + Integer.toString(parametro));
		}
		else{
			System.out.print("Autores vivos en el año: " + Integer.toString(parametro));
			for (Autor autor : autoresEnDB){
				if (autor.getDeath() >= parametro){
					System.out.print(autor.toString());
				}
				
			}	
		}
	}
	private static void ListarLibrosPorIdioma(){
		String parametro;
		while (true){
			try{
				System.out.println("Ingrese el idioma: ");
				parametro = stdin_reader.readLine();
				if (!parametro.isBlank()){
					break;
				}
				else{
					System.out.println("Parametro inválido, reintente");
				}
			}
			catch(Exception e){
				System.out.println("Entrada inválida");
			}
		}
		List<Libro> librosEnDB = databaseContext.getLibroRepository().findAll();
		if (librosEnDB.isEmpty()){
			System.out.print("No libros en ése idioma: " + parametro);
		}
		else{
			System.out.print("Libros escritos en: " + parametro);
			for (Libro libro : librosEnDB){
				if (libro.getIdioma() == parametro){
					System.out.print(libro.toString());
				}
				
			}	
		}
			
	}

	private static SSLContext configureTrustManager(String caCertPath) throws Exception {
        // Cargar el certificado de la CA
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (FileInputStream fis = new FileInputStream(caCertPath)) {
            ca = cf.generateCertificate(fis);
        }

        // Crear un KeyStore y añadir la CA
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null); // Inicializar un KeyStore vacío
        keyStore.setCertificateEntry("ca", ca);

        // Crear un TrustManager que use el KeyStore con la CA
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        // Configurar el SSLContext con el TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext;
    }


}
