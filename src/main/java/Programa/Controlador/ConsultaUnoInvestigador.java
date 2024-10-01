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

import java.util.List;

public class ConsultaUnoInvestigador {

    Comidas c = new Comidas();
    String uri = c.URI;
    
    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public ConsultaUnoInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
    }

    // Método para obtener el número total de productos registrados dentro del array "ingredients"
    public long obtenerNumeroTotalDeProductos() {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        long totalProductos = 0;

        try {
            // Cambia el ObjectId al valor correcto
            Document documento = collection.find(new Document("_id", new ObjectId("66f0cf78a3b564bde83f9d80"))).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Contar cuántos ingredientes hay en la lista
                totalProductos = ingredientes.size();
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el número total de productos: " + e.getMessage());
        }

        return totalProductos;
    }
}