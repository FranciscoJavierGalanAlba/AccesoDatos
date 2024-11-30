package dao;

import database.DBConecction;
import database.SchemaDB;
import model.Coches;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CochesDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    //1. Establezco la conexion a la bbdd
    public CochesDAO(){
        connection = new DBConecction().getConnection();
    }

    //2. Creo un metodo para añadir coches a la base de datoss
    public void addCoche(Coches coche) throws SQLException {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
                SchemaDB.TAB_CAR,
                SchemaDB.COL_MATRI, SchemaDB.COL_MARCA, SchemaDB.COL_MODEL, SchemaDB.COL_COLOR);

        //Preparo la consulta con los valores del nuevo coche
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, coche.getMatricula());
        preparedStatement.setString(2, coche.getMarca());
        preparedStatement.setString(3, coche.getModelo());
        preparedStatement.setString(4, coche.getColor());

        //ejecuto la query
        preparedStatement.execute();
    }

    //3.3 creo un metodo para obtener todos los coches de la base de datos
    public ArrayList<Coches> obtenerCoches() throws SQLException {
        String query = String.format("SELECT * FROM %s", SchemaDB.TAB_CAR);

        //preparo la consulta
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        //devuelvo una lista con todos los coches encontrados
        return getResultados(resultSet);
    }

    //4. Creo un metodo para eliminar coches
    public void eliminaCocheId(int id) throws SQLException {
        String query = String.format("DELETE FROM %s WHERE %s = ?",
                SchemaDB.TAB_CAR, SchemaDB.COL_ID);

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int filasEliminadas = preparedStatement.executeUpdate();

        if (filasEliminadas > 0) {
            System.out.println("Coche eliminado correctamente.");  // Passenger deleted successfully.
        } else {
            System.out.println("No se encontró ningún coche con el ID especificado.");  // No passenger found with the specified ID.
        }
    }

    //5. Creo un metodo para consultar coches por su id
    public ArrayList<Integer> obtenerCochesId() throws SQLException {
        String query = String.format("SELECT %s FROM %s",SchemaDB.COL_ID , SchemaDB.TAB_CAR);

        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        ArrayList<Integer> listaIds = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            listaIds.add(id);
        }

        return listaIds;
    }

    //6. creo un metodo para modificar coches por su id
    public void modificarCoches(int id, String matricula, String marca, String modelo, String color) throws SQLException {

        String query = "UPDATE " + SchemaDB.TAB_CAR +
                " SET " +
                SchemaDB.COL_MATRI + " = ?, " +
                SchemaDB.COL_MARCA + " = ?, " +
                SchemaDB.COL_MODEL + " = ?, " +
                SchemaDB.COL_COLOR + " = ? " +
                " WHERE " + SchemaDB.COL_ID + " = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, matricula);
        preparedStatement.setString(2, marca);
        preparedStatement.setString(3, modelo);
        preparedStatement.setString(4, color);
        preparedStatement.setInt(5, id);

        // Aquí está el cambio: utilizamos executeUpdate()
        int filasAfectadas = preparedStatement.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Coche actualizado correctamente.");
        } else {
            System.out.println("No se encontró ningún coche con el ID especificado.");
        }
    }

        //3.2 creo un metodo para convertir los resultados de la consulta en una lista de coches
    private ArrayList<Coches> getResultados (ResultSet datosResultantes) throws SQLException {
        ArrayList<Coches> listaCoches = new ArrayList<>();

        while(datosResultantes.next()){
            //extrae los valores de las columnas
            int id = resultSet.getInt(SchemaDB.COL_ID);
            String matricula = resultSet.getString(SchemaDB.COL_MATRI);
            String marca = resultSet.getString(SchemaDB.COL_MARCA);
            String modelo = resultSet.getString(SchemaDB.COL_MODEL);
            String color = resultSet.getString(SchemaDB.COL_COLOR);

            // Crea un objeto coche a partir de los valores y lo agrega a la lista
            listaCoches.add(mapearCoches(id, matricula, marca, modelo, color));
        }
        return listaCoches;
    }



    // 3.1 Creo un metodo para mapear los coches, es decir, ...
    public Coches mapearCoches(int id,String matricula, String marca, String modelo, String color){
        return new Coches(id,matricula, marca, modelo, color);
    }

}
