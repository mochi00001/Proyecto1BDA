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
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ConsultaTresInvestigador {

    Comidas c = new Comidas();
    String uri = c.URI;
    
    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public ConsultaTresInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
    }

    // Método para obtener el listado de productos que ya no están disponibles (exists_today = false o null)
    public List<Document> obtenerProductosNoDisponibles() {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        List<Document> productosNoDisponibles = new ArrayList<>();

        try {
            // Obtener el documento con los ingredientes
            Document documento = collection.find(new Document("_id", new ObjectId("66f0cf78a3b564bde83f9d80"))).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Iterar sobre los ingredientes y filtrar los que ya no están disponibles
                for (Document ingrediente : ingredientes) {
                    Boolean existeHoy = ingrediente.getBoolean("exists_today");
                    if (existeHoy == null || !existeHoy) {
                        // Agregar a la lista de productos no disponibles
                        productosNoDisponibles.add(ingrediente);
                    }
                }
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los productos no disponibles: " + e.getMessage());
        }

        return productosNoDisponibles;
    }
}