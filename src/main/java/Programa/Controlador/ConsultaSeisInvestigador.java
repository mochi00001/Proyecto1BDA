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
import java.util.Set;

public class ConsultaSeisInvestigador {

    Comidas c = new Comidas();
    String uri = c.URI;

    private MongoDatabase database;

    // Constructor para inicializar la conexión a la base de datos
    public ConsultaSeisInvestigador() {
        MongoClient mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ProyectoBDA1");  // Cambia el nombre de la base de datos si es diferente
    }

    // Método para obtener las recetas con restricciones de consumo basadas en los ingredientes
    public void obtenerRecetasConRestricciones() {
        MongoCollection<Document> collection = database.getCollection("Recetas");

        try {
            // Cambia el ObjectId al valor correcto
            Document documento = collection.find(new Document("_id", new ObjectId("66f0cf3fa3b564bde83f9d7b"))).first();

            if (documento != null) {
                // Obtener la lista de recetas
                List<Document> recetas = (List<Document>) documento.get("indigenous_recipes");

                for (Document receta : recetas) {
                    String nombreReceta = receta.getString("recipe_name");
                    List<Document> ingredientes = (List<Document>) receta.get("ingredients");

                    boolean tieneRestricciones = false;
                    StringBuilder restricciones = new StringBuilder("Restricciones en la receta '" + nombreReceta + "':\n");

                    for (Document ingrediente : ingredientes) {
                        String nombreIngrediente = ingrediente.getString("name_spanish");

                        // Buscar el ingrediente en la base de datos de ingredientes
                        Document ingredienteBD = buscarIngredienteEnBaseDeDatos(nombreIngrediente);
                        if (ingredienteBD != null) {
                            // Verificar las restricciones en "consumption_by_group"
                            Document consumoGrupo = (Document) ingredienteBD.get("consumption_by_group");

                            if (consumoGrupo != null) {
                                Set<String> grupos = consumoGrupo.keySet();
                                for (String grupo : grupos) {
                                    Document consumoEspecifico = (Document) consumoGrupo.get(grupo);
                                    String frecuencia = consumoEspecifico.getString("frequency");

                                    if (frecuencia != null) {
                                        if (frecuencia.equalsIgnoreCase("bajo")) {
                                            restricciones.append("- Restricción alta para el ingrediente ").append(nombreIngrediente).append(" en el grupo ").append(grupo).append("\n");
                                            tieneRestricciones = true;
                                        } else if (frecuencia.equalsIgnoreCase("moderado")) {
                                            restricciones.append("- Restricción moderada para el ingrediente ").append(nombreIngrediente).append(" en el grupo ").append(grupo).append("\n");
                                            tieneRestricciones = true;
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("Ingrediente " + nombreIngrediente + " no encontrado en la base de datos.");
                        }
                    }

                    if (tieneRestricciones) {
                        System.out.println(restricciones.toString());
                    } else {
                        System.out.println("La receta '" + nombreReceta + "' no tiene restricciones de consumo.\n");
                    }
                }
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener recetas con restricciones: " + e.getMessage());
        }
    }

    // Método para buscar un ingrediente por nombre en la base de datos de ingredientes
    private Document buscarIngredienteEnBaseDeDatos(String nombreIngrediente) {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        // Realizamos una búsqueda asegurándonos de que el campo 'ingredients.name_spanish' coincida con el nombre
        Document match = new Document("ingredients.name_spanish", nombreIngrediente);
        Document documento = collection.find(match).first();

        if (documento != null) {
            // Buscar el ingrediente específico dentro del array 'ingredients'
            List<Document> ingredientes = (List<Document>) documento.get("ingredients");
            for (Document ingrediente : ingredientes) {
                if (ingrediente.getString("name_spanish").equalsIgnoreCase(nombreIngrediente)) {
                    return ingrediente;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ConsultaSeisInvestigador consulta = new ConsultaSeisInvestigador();
        consulta.obtenerRecetasConRestricciones();
    }
}