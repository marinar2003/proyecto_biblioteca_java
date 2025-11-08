package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
    private Map<String, Libro> libros;

    public Biblioteca() {
        this.libros = new HashMap<>();
    }

    public void agregarLibro(Libro libro) throws IllegalArgumentException {
        if (libros.containsKey(libro.getIsbn())) {
            throw new IllegalArgumentException("El libro con ISBN " + libro.getIsbn() + " ya existe.");
        }
        libros.put(libro.getIsbn(), libro);
    }

    public Libro buscarLibroPorISBN(String isbn) {
        return libros.get(isbn);
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        List<Libro> resultado = new ArrayList<>();
       //TODO Agregar lógica

       for (Libro libro : libros.values()) {
            if (libro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                resultado.add(libro);
             }
        }
        return resultado;
    }

    public List<Libro> listarTodosLosLibros() {
        return new ArrayList<>(libros.values());
    }

    public void cargarLibrosDesdeCSV(String nombreArchivo) throws IOException {
        // TODO Agregar Lógica
        // usar metodo  agregarLibro()
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String libro;

         while ((libro = br.readLine()) != null) {

        // Si el archivo tiene encabezado, lo saltamos
            if (libro.toLowerCase().startsWith("titulo")) {
                continue;
            }

        String[] datos = libro.split(",");

        // Validación básica
            if (datos.length != 4) {
                System.out.println("Línea inválida en CSV: " + libro);
                continue;
            }       

        String titulo = datos[0];
        String autor = datos[1];
        int añoPublicacion = Integer.parseInt(datos[2]);
        String isbn = datos[3];

        try {
            agregarLibro(new Libro(titulo, autor, añoPublicacion, isbn));
        } catch (IllegalArgumentException e) {
        System.out.println("No se pudo agregar el libro: " + e.getMessage());
                }
            }   
        }
    }

    public void guardarLibrosEnCSV(String nombreArchivo) throws IOException {
        // TODO Agregar Lógica
        // el archivo debe contener: Titulo, Autor, Año de Publicacion, ISBN
          try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
        // Encabezado del archivo
        bw.write("Titulo,Autor,Año de Publicacion,ISBN");
        bw.newLine();

        for (Libro libro : libros.values()) {
            bw.write(libro.getTitulo() + "," +
                    libro.getAutor() + "," +
                    libro.getAñoPublicacion() + "," +
                    libro.getIsbn());
            bw.newLine();
            }
        }
    }
}
