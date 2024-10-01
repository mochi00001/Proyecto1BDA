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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultaCuatroInvestigador {

    Comidas c = new Comidas();
    String uri = c.URI;
    
    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public ConsultaCuatroInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
    }

    // Método para obtener las recetas asociadas a todas las festividades
    public Map<String, List<String>> obtenerRecetasPorFestividades() {
        MongoCollection<Document> collectionFestividades = database.getCollection("Festividad");
        MongoCollection<Document> collectionRecetas = database.getCollection("Recetas");

        Map<String, List<String>> recetasPorFestividad = new HashMap<>();

        try {
            // Obtener todas las festividades
            Document festividadesDoc = collectionFestividades.find(new Document("_id", new ObjectId("66f2351227369eb7c2e3b154"))).first();

            if (festividadesDoc != null) {
                // Obtener la lista de festividades
                List<Document> festividades = (List<Document>) festividadesDoc.get("indigenous_festivities");

                // Buscar recetas asociadas a festividades
                Document recetasDoc = collectionRecetas.find(new Document("_id", new ObjectId("66f0cf3fa3b564bde83f9d7b"))).first();

                if (recetasDoc != null) {
                    // Obtener la lista de recetas
                    List<Document> recetas = (List<Document>) recetasDoc.get("indigenous_recipes");

                    // Iterar sobre las festividades
                    for (Document festividad : festividades) {
                        String nombreFestividad = festividad.getString("Nombre_Original");
                        List<String> recetasParaFestividad = new ArrayList<>();

                        // Iterar sobre las recetas y filtrar las que están asociadas a festividades
                        for (Document receta : recetas) {
                            Boolean usadaEnFestividades = receta.getBoolean("used_for_festivities");
                            if (usadaEnFestividades != null && usadaEnFestividades) {
                                // Agregar la receta a la lista de recetas asociadas a esta festividad
                                recetasParaFestividad.add(receta.getString("recipe_name"));
                            }
                        }

                        // Solo agregar la festividad si tiene recetas asociadas
                        if (!recetasParaFestividad.isEmpty()) {
                            recetasPorFestividad.put(nombreFestividad, recetasParaFestividad);
                        }
                    }
                } else {
                    System.out.println("No se encontraron recetas.");
                }
            } else {
                System.out.println("No se encontraron festividades.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener recetas por festividades: " + e.getMessage());
        }

        return recetasPorFestividad;
    }
}