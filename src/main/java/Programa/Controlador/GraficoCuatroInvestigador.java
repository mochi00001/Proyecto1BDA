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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class GraficoCuatroInvestigador extends JFrame {

    Comidas c = new Comidas();
    String uri = c.URI;  // URI de conexión a MongoDB

    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public GraficoCuatroInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
        setTitle("Gráfico de productos más populares en el país");
        setSize(800, 600);  // Ajustar el tamaño del frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Método para obtener todos los ingredientes desde MongoDB
    public List<Document> obtenerIngredientes() {
        MongoCollection<Document> collectionIngredientes = database.getCollection("Ingredientes");
        List<Document> ingredientesList = new ArrayList<>();

        // Obtener todos los documentos de la colección Ingredientes
        for (Document doc : collectionIngredientes.find()) {
            ingredientesList.addAll((List<Document>) doc.get("ingredients"));
        }

        return ingredientesList;
    }

    // Método para obtener la popularidad de los productos
    public void mostrarGraficoDeBarras() {
        List<Document> ingredientes = obtenerIngredientes();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String locationPais = "Desconocido";  // Por defecto, si no se encuentra la ubicación.

        // Recorrer los ingredientes y sumar la frecuencia de los grupos indígenas extraídos dinámicamente
        for (Document ingrediente : ingredientes) {
            if (ingrediente.containsKey("name_spanish")) {
                String nombre = ingrediente.getString("name_spanish");
                Document consumoGrupo = (Document) ingrediente.get("consumption_by_group");

                // Extraer la parte antes de la coma en production_location
                if (ingrediente.containsKey("production_location")) {
                    String location = ingrediente.getString("production_location");
                    if (location != null && location.contains(",")) {
                        locationPais = location.split(",")[0];  // Obtener la parte antes de la coma
                    } else {
                        locationPais = location != null ? location : "Desconocido";  // Usar la ubicación completa si no hay coma
                    }
                }

                if (consumoGrupo != null) {
                    // Sumar las frecuencias de los grupos indígenas extraídos dinámicamente
                    double totalFrecuencia = 0.0;

                    // Obtener los nombres de los grupos dinámicamente
                    Set<String> grupos = consumoGrupo.keySet();
                    for (String grupo : grupos) {
                        Document consumoEspecifico = (Document) consumoGrupo.get(grupo);
                        if (consumoEspecifico != null) {
                            String frecuencia = consumoEspecifico.getString("frequency");

                            switch (frecuencia.toLowerCase()) {
                                case "alto":
                                    totalFrecuencia += 3.0;
                                    break;
                                case "moderado":
                                    totalFrecuencia += 2.0;
                                    break;
                                case "bajo":
                                    totalFrecuencia += 1.0;
                                    break;
                                default:
                                    totalFrecuencia += 0.0;
                                    break;
                            }
                        }
                    }

                    // Agregar el producto y la frecuencia total al dataset
                    dataset.addValue(totalFrecuencia, "Popularidad en " + locationPais, nombre);
                }
            }
        }

        // Crear el gráfico de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Productos más populares en " + locationPais, // Título que incluye la ubicación
                "Productos", // Eje X
                "Frecuencia acumulada", // Eje Y
                dataset, // Datos
                PlotOrientation.VERTICAL, // Orientación
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true);  // Mostrar líneas de cuadrícula

        // Mostrar el gráfico en un panel
        ChartPanel chartPanel = new ChartPanel(barChart);
        setContentPane(chartPanel);

        // Ajustar el tamaño de la ventana al contenido y hacerla visible
        pack();
        setVisible(true);
    }
}