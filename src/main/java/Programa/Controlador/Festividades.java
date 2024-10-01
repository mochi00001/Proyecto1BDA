package Programa.Controlador;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Updates.set;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class Festividades {
    
    Comidas c = new Comidas();
    String uri = c.URI;
    ObjectId objId = new ObjectId("66f2351227369eb7c2e3b154");

    
    private MongoDatabase database;

    // Constructor que establece la conexión a MongoDB
    public Festividades() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            this.database = mongoClient.getDatabase("ProyectoBDA1");
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Método para insertar una nueva festividad
    public void insertarFestividad(String nombre, String fecha, String descripcion, String asistencia, String implicaciones) {
        MongoCollection<Document> collection = database.getCollection("Festividad");

        Document festividad = new Document("nombre", nombre)
                .append("fecha", fecha)
                .append("descripcion", descripcion)
                .append("asistencia", asistencia)
                .append("implicaciones", implicaciones);

        collection.insertOne(festividad);
        System.out.println("Festividad registrada correctamente.");
    }

    // Método para eliminar una festividad por su nombre
    public void eliminarFestividad(String nombre) {
        MongoCollection<Document> collection = database.getCollection("Festividad");
        collection.deleteOne(new Document("nombre", nombre));
        System.out.println("Festividad eliminada correctamente.");
    }
    
     // Función para eliminar una festividad específica por nombre
    public void eliminarFestividadPorNombre(String nombreFestividad) {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Festividad");

            // Eliminar la festividad específica de la lista
            collection.updateOne(eq("_id", objId),
                    pull("indigenous_festivities", eq("Nombre_Original", nombreFestividad)));

            System.out.println("Festividad eliminada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar la festividad: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    // Método para modificar una festividad por su nombre
    public void modificarFestividad(String nombreFestividad, String nuevaFecha, String nuevasActividades, String nuevoQuienPuedeAsistir, String nuevasImplicaciones) {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Festividad");

            // Actualización de la festividad en la lista (se actualizan todos los campos relevantes)
            collection.updateOne(eq("_id", objId),
                    combine(
                            set("indigenous_festivities.$[elem].Fecha", nuevaFecha),
                            set("indigenous_festivities.$[elem].Actividades", nuevasActividades),
                            set("indigenous_festivities.$[elem].Quien_Puede_Asistir", nuevoQuienPuedeAsistir),
                            set("indigenous_festivities.$[elem].Implicaciones", nuevasImplicaciones)
                    ),
                    new com.mongodb.client.model.UpdateOptions().arrayFilters(List.of(eq("elem.Nombre_Original", nombreFestividad)))
            );

            System.out.println("Festividad modificada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al modificar festividad: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }
    
    // Método para consultar todas las festividades
    public List<Document> consultarFestividades() {
        List<Document> festividades = new ArrayList<>();
        
        try {
            MongoCollection<Document> collection = database.getCollection("Festividad");
            MongoCursor<Document> cursor = collection.find().iterator();
            
            try {
                while (cursor.hasNext()) {
                    Document festividad = cursor.next();
                    festividades.add(festividad);
                }
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            System.err.println("Error al consultar festividades: " + e.getMessage());
        }
        
        return festividades;
    }
    
    public void agregarFestividad(String nombreOriginal, String fecha, String actividades, String quienPuedeAsistir, String implicaciones) {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Festividad");

            // Crear el nuevo documento de festividad
            Document nuevaFestividad = new Document("Nombre_Original", nombreOriginal)
                    .append("Fecha", fecha)
                    .append("Actividades", actividades)
                    .append("Quien_Puede_Asistir", quienPuedeAsistir)
                    .append("Implicaciones", implicaciones);

            // Actualizar el documento agregando la nueva festividad a la lista
            collection.updateOne(
                new Document("_id", objId),
                new Document("$push", new Document("indigenous_festivities", nuevaFestividad))
            );

            System.out.println("Festividad agregada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al agregar festividad: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }
    
    
    public boolean festividadExiste(String nombreOriginal) {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Festividad");

            // Obtener el documento
            Document document = collection.find(new Document("_id", objId)).first();

            if (document != null) {
                // Obtener la lista de festividades indígenas
                List<Document> festividades = (List<Document>) document.get("indigenous_festivities");

                // Buscar si alguna festividad tiene el mismo nombre
                for (Document festividad : festividades) {
                    if (festividad.getString("Nombre_Original").equalsIgnoreCase(nombreOriginal)) {
                        return true; // Festividad encontrada
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al verificar festividad: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return false; // Festividad no encontrada
    }
    
    public List<Document> obtenerFestividades() {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Festividad");

            // Obtener el documento
            Document document = collection.find(new Document("_id", objId)).first();

            if (document != null) {
                // Retornar la lista de festividades indígenas
                return (List<Document>) document.get("indigenous_festivities");
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener festividades: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return null;
    }  
    
    public Document obtenerFestividadPorNombre(String nombreFestividad) {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Festividad");

            // Obtener el documento que contiene las festividades
            Document document = collection.find(new Document("_id", objId)).first();

            if (document != null) {
                // Obtener la lista de festividades
                List<Document> festividades = (List<Document>) document.get("indigenous_festivities");

                // Buscar la festividad por nombre
                for (Document festividad : festividades) {
                    if (festividad.getString("Nombre_Original").equalsIgnoreCase(nombreFestividad)) {
                        return festividad; // Retornar la festividad encontrada
                    }
                }
                System.out.println("No se encontró una festividad con ese nombre.");
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener festividad: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return null;
    }
}