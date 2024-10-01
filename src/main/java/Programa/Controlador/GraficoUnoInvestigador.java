/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Programa.Controlador;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class GraficoUnoInvestigador extends JFrame {

    Comidas c = new Comidas();
    String uri = c.URI;  // URI de conexión a MongoDB

    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public GraficoUnoInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
        setTitle("Gráfico de productos en recetas");
        setSize(800, 600);  // Ajustar el tamaño del frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Método para obtener todas las recetas desde MongoDB
    public List<Document> obtenerRecetas() {
        MongoCollection<Document> collectionRecetas = database.getCollection("Recetas");
        Document documentoRecetas = collectionRecetas.find().first();  // Obtener el primer documento de recetas

        if (documentoRecetas != null) {
            return (List<Document>) documentoRecetas.get("indigenous_recipes");  // Obtener la lista de recetas
        } else {
            System.out.println("No se encontraron recetas en la base de datos.");
            return new ArrayList<>();  // Retornar una lista vacía si no se encontraron recetas
        }
    }

   
    // Método para mostrar el gráfico de pastel con los ingredientes de la receta seleccionada por ID
    public void mostrarGraficoDePastel(int idReceta) {
        List<Document> recetas = obtenerRecetas();

        // Verificar si el ID de la receta es válido
        if (idReceta < 0 || idReceta >= recetas.size()) {
            System.out.println("ID de receta no válido.");
            return;
        }

        // Obtener la receta seleccionada por ID
        Document recetaSeleccionada = recetas.get(idReceta);

        // Crear el conjunto de datos para el gráfico de pastel
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Obtener los ingredientes de la receta seleccionada
        List<Document> ingredientes = (List<Document>) recetaSeleccionada.get("ingredients");

        for (Document ingrediente : ingredientes) {
            String nombre = ingrediente.getString("name_spanish");
            String cantidad = ingrediente.getString("quantity");

            // Si la cantidad no es numérica, asignamos un valor predeterminado (ejemplo: 1)
            double cantidadNumerica;
            try {
                cantidadNumerica = Double.parseDouble(cantidad.replaceAll("[^0-9.]", ""));
            } catch (NumberFormatException e) {
                // Asignar un valor predeterminado si no es numérico
                System.out.println("Cantidad no numérica para " + nombre + ": " + cantidad + ". Usando valor predeterminado 1.");
                cantidadNumerica = 1.0;
            }

            // Agregar el ingrediente al dataset
            dataset.setValue(nombre, cantidadNumerica);
        }

        // Crear el gráfico de pastel
        JFreeChart chart = ChartFactory.createPieChart(
                "Proporción de ingredientes en " + recetaSeleccionada.getString("recipe_name"), // Título
                dataset, // Datos
                true, // Mostrar leyenda
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSimpleLabels(true);  // Mostrar etiquetas simples

        // Mostrar el gráfico en un panel
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);

        // Ajustar el tamaño de la ventana al contenido y hacerla visible
        pack();  // Ajustar el tamaño del frame a los componentes
        setVisible(true);  // Asegurarse de que la ventana sea visible
    }
}