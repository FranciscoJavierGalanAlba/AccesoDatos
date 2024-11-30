package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Alumnos;
import dao.ProfesoresDAO;
import model.Profesores;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class DBConnection {
    private String connectionString = "mongodb+srv://%s:%s@cluster0.nlemf.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private MongoClient mongoClient;
    private CodecProvider codecProvider; //explicacion abajo
    CodecRegistry codecRegistry;

    //1. Creo constructor que se encarga de establecer la conexion a la BBDD mongo
    public DBConnection(){
        codecProvider = PojoCodecProvider.builder().automatic(true).build();
        codecRegistry = CodecRegistries.fromRegistries(
                MongoClients.create().getCodecRegistry(),
                CodecRegistries.fromProviders(codecProvider)
        );

        mongoClient = MongoClients.create(String.format(connectionString, SchemaDB.USER, SchemaDB.PASS));
    }

    //2. Creo metodo para obtener las colecciones
    public MongoCollection getAlumnosCollection(){
        MongoDatabase database = mongoClient.getDatabase("centro_estudios").withCodecRegistry(codecRegistry);
        return database.getCollection("alumnos", Alumnos.class);
    }

    public MongoCollection getProfesoresCollection(){
        MongoDatabase database = mongoClient.getDatabase("centro_estudios").withCodecRegistry(codecRegistry);
        return database.getCollection("profesores", Profesores.class);
    }
}

//codecProvider = PojoCodecProvider.builder().automatic(true).build();
/*
 *  -Crea un proveedor de codecs que permite la serialización y
 * deserialización automática de objetos.
 *  -Los codecs son necesarios para convertir objetos Java en documentos
 * BSON (el formato de datos de MongoDB) y viceversa.
 */

/*
 *codecRegistry = CodecRegistries.fromRegistries(
                MongoClients.create().getCodecRegistry(),
                CodecRegistries.fromProviders(codecProvider)
        );
 *Registra los codecs para los tipos de objetos que vamos a utilizar (Usuario, Coches, Ciclos).
 *Esto permite a MongoDB entender cómo representar estos objetos en la base de datos.
 */

//
