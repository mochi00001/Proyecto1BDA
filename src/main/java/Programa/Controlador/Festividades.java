package Programa.Controlador;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class Festividades {
    private final String uri = "mongodb+srv://dbLuis:dbLuis12@laboratorio1.zhb0x.mongodb.net/ProyectoBDA1";
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
        MongoCollection<Document> collection = database.getCollection("festividades");

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
        MongoCollection<Document> collection = database.getCollection("festividades");
        collection.deleteOne(new Document("nombre", nombre));
        System.out.println("Festividad eliminada correctamente.");
    }

    // Método para obtener todas las festividades
    public List<Document> obtenerFestividades() {
        MongoCollection<Document> collection = database.getCollection("festividades");
        List<Document> festividades = collection.find().into(new ArrayList<>());
        return festividades;
    }

    // Método para modificar una festividad por su nombre
    public void modificarFestividad(String nombre, String nuevaFecha, String nuevaDescripcion, String nuevaAsistencia, String nuevasImplicaciones) {
        MongoCollection<Document> collection = database.getCollection("festividades");
        Document query = new Document("nombre", nombre);

        Document nuevosDatos = new Document("fecha", nuevaFecha)
                .append("descripcion", nuevaDescripcion)
                .append("asistencia", nuevaAsistencia)
                .append("implicaciones", nuevasImplicaciones);

        Document update = new Document("$set", nuevosDatos);
        collection.updateOne(query, update);
        System.out.println("Festividad modificada correctamente.");
    }
    
    // Método para consultar todas las festividades
    public List<Document> consultarFestividades() {
        List<Document> festividades = new ArrayList<>();
        
        try {
            MongoCollection<Document> collection = database.getCollection("festividades");
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
    
}
