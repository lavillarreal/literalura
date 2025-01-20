package com.alura.literalura;

import com.alura.literalura.models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.text.DecimalFormat;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

	public static List<Libro> parseLibrosFromJson(String jsonResponse) {
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
			libro.setNumeroDescargas(bookJson.get("download_count").getAsInt());

			// Procesar autores
			JsonArray authorsJson = bookJson.getAsJsonArray("authors");

			System.out.print("Libro añadido: " + libro.getTitulo());
			System.out.print("Agregando autores... ");
			List<Autor> autores = new ArrayList<>();
			for (JsonElement authorElement : authorsJson) {
				JsonObject authorJson = authorElement.getAsJsonObject();
				Autor autor = new Autor();
				autor.setNombre(authorJson.get("name").getAsString());
				autor.setApellido(""); // Asumimos que no hay apellido separado en el JSON
				autor.setBirth(authorJson.get("birth_year").getAsInt());
				autor.setDeath(authorJson.has("death_year") ? authorJson.get("death_year").getAsInt() : 0);
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
				URL url = new URL(apiUrl + titulo);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json");

				int responseCode = connection.getResponseCode();

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
			} catch (Exception e) {
				System.out.println("Error al realizar la solicitud HTTP: " + e.getMessage());
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




}
