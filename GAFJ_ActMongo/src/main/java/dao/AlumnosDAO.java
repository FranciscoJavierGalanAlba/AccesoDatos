package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import model.Alumnos;
import database.DBConnection;
import org.bson.conversions.Bson;

public class AlumnosDAO {
    MongoCollection collectionAlumnos;
    Alumnos alumnos;

    //1. creo la conexion a la base de datos y a la coleccion
    public AlumnosDAO(){
        collectionAlumnos = new DBConnection().getAlumnosCollection();
        alumnos = new Alumnos();
    }

    //metodo para insertar alumnos
    public void insertarAlumno(Alumnos alumnos){
        MongoCollection collection = new DBConnection().getAlumnosCollection();
        collection.insertOne(alumnos);
    }

    public void dameAlumnos(){
        MongoCollection collection = new DBConnection().getAlumnosCollection();
        FindIterable<Alumnos> iterable = collection.find(Alumnos.class);
        MongoCursor<Alumnos> cursor = iterable.cursor();
        while(cursor.hasNext()){
            Alumnos alumnos = cursor.next();
            alumnos.mostrarDatos();
            System.out.println("----");
        }
    }

    public void dameAlumnosEmail(String email){
        Bson filtrado = Filters.and(Filters.eq("email", email));

        MongoCollection collection = new DBConnection().getAlumnosCollection();

        FindIterable<Alumnos> iterable = collection.find(filtrado);
        MongoCursor<Alumnos> cursor = iterable.cursor();
        while(cursor.hasNext()) {
            Alumnos alumnos = cursor.next();
            alumnos.mostrarDatos();
        }
    }

    public void eliminarAlumno() {
        // documento filtro ->
        Bson filtrado =
                Filters.and(Filters.lt("rating" , 5));

        MongoCollection collecion = new DBConnection().getAlumnosCollection();
        collecion.deleteMany(filtrado);
    }
}
