package com.alura.literalura;

import com.alura.literalura.models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.text.DecimalFormat;
import javax.net.ssl.*;
import java.security.cert.Certificate;


import com.google.gson.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(LiteraluraApplication.class, args);
		BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.print("Buscar por titulo\nListarLibrosRegistrados\nListarAutoresRegistrados\nListarAutoresVivosEnUnAño\nListarLibrosPorIdioma\nSalir");

			String input = stdin_reader.readLine();

			// Aquí puedes agregar lógica para manejar las entradas
			if (input.equals("Salir")) {
				break;  // Para salir del bucle si el usuario escribe "Salir"
			}
			else if (input.equals("1")) {
				BuscarPorTitulo();
			}
			else{
				break;
			}
		}

	}

	public static List<Libro> parseLibrosFromJson(String jsonResponse) throws IOException {
		List<Libro> libros = new ArrayList<>();
		Gson gson = new Gson();

		// Deserializar JSON
		JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
		JsonArray results = root.getAsJsonArray("results");
		System.out.print("Resultados parseado");

		for (JsonElement element : results) {
			JsonObject bookJson = element.getAsJsonObject();
			Libro libro = new Libro();

			// Asignar datos básicos
			libro.setTitulo(bookJson.get("title").getAsString());
			libro.setIdioma(bookJson.getAsJsonArray("languages").get(0).getAsString());
			libro.setnumero_descargas(bookJson.get("download_count").getAsInt());

			// Procesar autores
			JsonArray authorsJson = bookJson.getAsJsonArray("authors");

			System.out.print("Libro añadido: " + libro.getTitulo());
			System.out.print(authorsJson);
			System.out.print("Agregando autores... ");
			List<Autor> autores = new ArrayList<>();
			String[] authorName;
			for (JsonElement authorElement : authorsJson) {
				JsonObject authorJson = authorElement.getAsJsonObject();
				Autor autor = new Autor();
				authorName = authorJson.get("name").getAsString().split(",");
				autor.setNombre(authorName[1]);
				autor.setApellido(authorName[0]);
				try{
					autor.setBirth(authorJson.has("birth_year") ? authorJson.get("birth_year").getAsInt() : 0);
				}
				catch (java.lang.UnsupportedOperationException e){
					autor.setBirth(0);
					System.out.println("Cant get birth date for author: " + autor.getNombre());
				}
				catch (Exception e){
					System.out.println(e.getClass());
				}
				try{
					autor.setDeath(authorJson.has("death_year") ? authorJson.get("death_year").getAsInt() : 0);
				}
				catch (java.lang.UnsupportedOperationException e){
					autor.setDeath(0);
					System.out.println("Cant get death date for author: " + autor.getNombre());
				}
				catch (Exception e){
					System.out.println(e.getClass());
				}
				autores.add(autor);
			}
			libro.setAutores(autores);

			libros.add(libro);

		}
		return libros;
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
					System.out.print("Respuesta obtenida");
					List<Libro> libros = parseLibrosFromJson(jsonResponse);

					for (Libro libro : libros) {
						System.out.println("Título: " + libro.getTitulo());
						System.out.println("Idioma: " + libro.getIdioma());
						System.out.println("Descargas: " + libro.getnumero_descargas());
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

	private static void ListarLibrosRegistrados(){

	}
	private static void ListarAutoresRegistrados(){

	}
	private static void ListarAutoresVivosEnUnAño(){

	}
	private static void ListarLibrosPorIdioma(){

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
