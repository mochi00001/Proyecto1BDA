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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultaDosInvestigador {

    Comidas c = new Comidas();
    String uri = c.URI;
    
    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public ConsultaDosInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
    }

    // Método para obtener la cantidad de productos registrados por cada lengua indígena
    public Map<String, Integer> obtenerCantidadDeProductosPorLengua() {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        Map<String, Integer> productosPorLengua = new HashMap<>();

        try {
            // Obtener el documento con los ingredientes
            Document documento = collection.find(new Document("_id", new ObjectId("66f0cf78a3b564bde83f9d80"))).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Iterar sobre los ingredientes
                for (Document ingrediente : ingredientes) {
                    // Obtener los nombres en lenguas indígenas
                    Document nombresIndigenas = (Document) ingrediente.get("names_indigenous_languages");

                    if (nombresIndigenas != null) {
                        for (String lengua : nombresIndigenas.keySet()) {
                            // Incrementar el conteo para cada lengua
                            productosPorLengua.put(lengua, productosPorLengua.getOrDefault(lengua, 0) + 1);
                        }
                    }
                }
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la cantidad de productos por lengua: " + e.getMessage());
        }

        return productosPorLengua;
    }
}