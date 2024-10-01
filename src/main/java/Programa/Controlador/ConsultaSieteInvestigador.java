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

import java.util.*;

public class ConsultaSieteInvestigador {

    Comidas c = new Comidas();
    String uri = c.URI;
    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public ConsultaSieteInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
    }

    // Método para generar el reporte de alimentos menos utilizados y los grupos que los consumen
    public Map<String, List<String>> generarReporteDeAlimentosMenosUtilizados() {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        Map<String, List<String>> reporte = new HashMap<>();

        try {
            List<Document> documentos = (List<Document>) collection.find().into(new ArrayList<>());

            for (Document documento : documentos) {
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                for (Document ingrediente : ingredientes) {
                    String nombreIngrediente = ingrediente.getString("name_spanish");
                    Document consumoGrupo = (Document) ingrediente.get("consumption_by_group");

                    // Extraer la región antes de la coma en production_location
                    String productionLocation = ingrediente.getString("production_location");
                    String region = "Desconocido";
                    if (productionLocation != null && productionLocation.contains(",")) {
                        region = productionLocation.split(",")[0].trim();
                    } else if (productionLocation != null) {
                        region = productionLocation.trim();
                    }

                    if (consumoGrupo != null) {
                        List<String> gruposReporte = new ArrayList<>();
                        Set<String> grupos = consumoGrupo.keySet();

                        for (String grupo : grupos) {
                            Document consumoEspecifico = (Document) consumoGrupo.get(grupo);
                            String frecuencia = consumoEspecifico.getString("frequency");

                            if (frecuencia != null) {
                                gruposReporte.add(grupo + ": " + frecuencia);
                            }
                        }

                        // Añadir el reporte al Map
                        if (!gruposReporte.isEmpty()) {
                            reporte.put(nombreIngrediente + " (Región: " + region + ")", gruposReporte);
                        } else {
                            reporte.put(nombreIngrediente + " (Región: " + region + ")", Collections.singletonList("Sin datos de consumo"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
        return reporte;
    }
}