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
					System.out.println(jsonResponse);

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
