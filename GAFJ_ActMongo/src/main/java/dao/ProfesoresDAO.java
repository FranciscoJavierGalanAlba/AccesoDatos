package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import database.DBConnection;
import model.Alumnos;
import model.Profesores;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ProfesoresDAO {
    MongoCollection collectionProfesores;
    Profesores profesores;

    public ProfesoresDAO(){
        collectionProfesores = new DBConnection().getProfesoresCollection();
        profesores = new Profesores();
    }

    //metodo para insertar profesores
    public void insertarProfesores(Profesores profesores){
        MongoCollection collection = new DBConnection().getProfesoresCollection();
        collection.insertOne(profesores);
    }

    public void dameProfesores(){
        MongoCollection collection = new DBConnection().getProfesoresCollection();
        FindIterable<Profesores> iterable = collection.find(Profesores.class);
        MongoCursor<Profesores> cursor = iterable.cursor();
        while(cursor.hasNext()){
            Profesores profesores = cursor.next();
            profesores.mostrarDatos();
            System.out.println("-----");
        }
    }

    public void mostarProfesorEdad(int edad1, int edad2){
        Bson filtrado =
                Filters.and(Filters.gt("age", edad1), Filters.lt("age", edad2));

        MongoCollection collection = new DBConnection().getProfesoresCollection();
        FindIterable<Profesores> iterable = collection.find(filtrado);
        MongoCursor<Profesores> cursor = iterable.cursor();
        while(cursor.hasNext()){
            Profesores profesores = cursor.next();
            profesores.mostrarDatos();
            System.out.println("-----");
        }
    }

    public void actualizarCalifiProfe(String email, double nuevaCalificacion){
        Bson filtro = Filters.eq("email", email);
        MongoCollection collection = new DBConnection().getProfesoresCollection();


        Bson update = Updates.combine(
                Updates.set("rating", nuevaCalificacion)
        );

        collection.updateOne(filtro, update);

        System.out.println("Calificación actualizada para el profesor con email: " + email);
    }

}
