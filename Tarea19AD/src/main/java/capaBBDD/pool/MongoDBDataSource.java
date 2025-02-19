package capaBBDD.pool;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBDataSource {
    private static final String URI = "mongodb://localhost:27017"; // Reemplaza con tu URI
    private static final String DATABASE_NAME = "Tarea19"; // Reemplaza con el nombre de tu BD
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        try {
            mongoClient = MongoClients.create(URI);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MongoDBDataSource() {}

    public static MongoDatabase getDatabase() {
        return database;
    }
}
