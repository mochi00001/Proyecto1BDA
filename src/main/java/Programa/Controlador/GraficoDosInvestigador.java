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

public class GraficoDosInvestigador extends JFrame {

    Comidas c = new Comidas();
    String uri = c.URI;  // URI de conexión a MongoDB

    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public GraficoDosInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
        setTitle("Gráfico de proporción de alimentos por grupo indígena");
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

    // Método para obtener los grupos indígenas desde MongoDB
    public List<String> obtenerGruposIndigenas() {
        MongoCollection<Document> collectionPoblaciones = database.getCollection("Grupos");
        List<String> gruposIndigenas = new ArrayList<>();

        // Obtener todos los grupos indígenas
        for (Document doc : collectionPoblaciones.find()) {
            List<Document> grupos = (List<Document>) doc.get("indigenous_groups");
            for (Document grupo : grupos) {
                gruposIndigenas.add(grupo.getString("name"));
            }
        }

        return gruposIndigenas;
    }

    // Mostrar los grupos indígenas disponibles para seleccionar
    public void mostrarGruposIndigenas() {
        List<String> gruposIndigenas = obtenerGruposIndigenas();
        System.out.println("Grupos indígenas disponibles:");

        for (int i = 0; i < gruposIndigenas.size(); i++) {
            System.out.println((i + 1) + " - " + gruposIndigenas.get(i));
        }
    }

    // Método para mostrar el gráfico de pastel con la proporción de alimentos por grupo indígena
    public void mostrarGraficoDeAlimentos(int idGrupo) {
        List<Document> ingredientes = obtenerIngredientes();
        List<String> gruposIndigenas = obtenerGruposIndigenas();

        // Verificar si el ID de grupo es válido
        if (idGrupo < 1 || idGrupo > gruposIndigenas.size()) {
            System.out.println("ID de grupo indígena no válido.");
            return;
        }

        // Crear el conjunto de datos para el gráfico de pastel
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Seleccionar el grupo indígena basado en el ID proporcionado
        String grupoSeleccionado = gruposIndigenas.get(idGrupo - 1);

        // Recorrer los ingredientes y extraer la frecuencia de uso del grupo seleccionado
        for (Document ingrediente : ingredientes) {
            if (ingrediente.containsKey("name_spanish")) {
                String nombre = ingrediente.getString("name_spanish");
                Document consumoGrupo = (Document) ingrediente.get("consumption_by_group");

                // Verificar si el campo consumption_by_group existe
                if (consumoGrupo == null) {
                    System.out.println("No hay información de consumo para el ingrediente " + nombre);
                    continue;
                }

                Document consumoEspecifico = (Document) consumoGrupo.get(grupoSeleccionado);

                if (consumoEspecifico != null) {
                    String frecuencia = consumoEspecifico.getString("frequency");

                    // Convertir la frecuencia a un valor numérico
                    double valorFrecuencia = 0.0;
                    switch (frecuencia.toLowerCase()) {
                        case "alto":
                            valorFrecuencia = 3.0;
                            break;
                        case "moderado":
                            valorFrecuencia = 2.0;
                            break;
                        case "bajo":
                            valorFrecuencia = 1.0;
                            break;
                        default:
                            valorFrecuencia = 0.0;  // Si no se encuentra, asignar un valor predeterminado
                    }

                    // Agregar el ingrediente al dataset
                    dataset.setValue(nombre, valorFrecuencia);
                } else {
                    System.out.println("No hay información de consumo para el grupo " + grupoSeleccionado + " en el ingrediente " + nombre);
                }
            } else {
                System.out.println("El ingrediente no tiene un campo 'name_spanish'.");
            }
        }

        // Crear el gráfico de pastel
        JFreeChart chart = ChartFactory.createPieChart(
                "Proporción de alimentos usados por el grupo " + grupoSeleccionado, // Título
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