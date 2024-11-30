package dao;

import database.DBConecction;
import database.SchemaDB;
import model.Coches;
import model.Pasajeros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PasajerosDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public PasajerosDAO(){
        connection = new DBConecction().getConnection();
    }

    //1. metodo para añadir pasajeros
    public void addPasajero(Pasajeros pasajeros) throws SQLException {
        String query = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?,?,?)",
                SchemaDB.TAB_PAS,
                SchemaDB.COL_NAM_PAS, SchemaDB.COL_AGE_PAS, SchemaDB.COL_PES_PAS);

        //Preparo la consulta con los valores del nuevo coche
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, pasajeros.getNombre());
        preparedStatement.setInt(2, pasajeros.getEdad());
        preparedStatement.setInt(3, pasajeros.getPeso());

        //ejecuto la query
        preparedStatement.execute();
    }

    //2.3 creo un metodo para obtener todos los pasajeros
    public ArrayList<Pasajeros> obtenerPasajeros() throws SQLException {
        String query = String.format("SELECT * FROM %s", SchemaDB.TAB_PAS);

        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        return getPasajeros(resultSet);
    }

    //3. creo un metodo para borrar pasajeros
    public void eliminarPasajeroId(int id) throws SQLException {
        String query = String.format("DELETE FROM %s WHERE %s = ?",
                SchemaDB.TAB_PAS, SchemaDB.COL_ID_PAS);

        // prepare connection
        preparedStatement = connection.prepareStatement(query);
        // set parameter for ID to delete
        preparedStatement.setInt(1, id);

        // execute the query
        int filasEliminadas = preparedStatement.executeUpdate();

        if (filasEliminadas > 0) {
            System.out.println("Pasajero eliminado correctamente.");  // Passenger deleted successfully.
        } else {
            System.out.println("No se encontró ningún pasajero con el ID especificado.");  // No passenger found with the specified ID.
        }
    }

    //Consultar pasajero por id
    public ArrayList<Integer> obtenerPasajeroId() throws SQLException {
        String query = String.format("SELECT %S FROM %s",
                SchemaDB.COL_ID_PAS, SchemaDB.TAB_PAS);

        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        ArrayList<Integer> listaIds = new ArrayList<>();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            listaIds.add(id);
        }
        return listaIds;
    }

    //añadir pasajero a coche
    public void insertarPasajeroCoche(int idCoch, int idPasa) throws SQLException {
        String query = String.format("UPDATE %s SET %s=? WHERE %s=?",
                SchemaDB.TAB_PAS, SchemaDB.COL_ID_COC_PAS, SchemaDB.COL_ID_PAS);

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idCoch);
        preparedStatement.setInt(2, idPasa);
        preparedStatement.executeUpdate();
        System.out.println("Pasajero añadido a coche.");

    }

    public void deletePasajeroCoche(int idCoc, int idPas) throws SQLException {
        String query = String.format("DELETE FROM %s WHERE %S = ? AND %s = ?",
                SchemaDB.TAB_PAS, SchemaDB.COL_ID_PAS, SchemaDB.COL_ID_COC_PAS) ;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idPas);
        preparedStatement.setInt(2, idCoc);
        int ColBorrada = preparedStatement.executeUpdate();

        if (ColBorrada > 0) {
            System.out.println("Pasajero eliminado correctamente del coche.");
        } else {
            System.out.println("No se encontró ninguna asignación para el pasajero y el coche especificados.");
        }
    }

    public void listarPasajerosDeCoche(int idCoche) throws SQLException {
        String query = String.format("SELECT p.* " +
                        "FROM %s c " +
                        "INNER JOIN %s a ON c.%s = a.%s " +
                        "INNER JOIN %s p ON a.%s = p.%s " +
                        "WHERE c.%s = ?",
                SchemaDB.TAB_CAR,
                SchemaDB.TAB_ASIG, SchemaDB.COL_ID, SchemaDB.COL_ID_ASIG_CO,
                SchemaDB.TAB_PAS, SchemaDB.COL_ID_ASIG_PA, SchemaDB.COL_ID_PAS,
                SchemaDB.COL_ID);


        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idCoche);

        resultSet = preparedStatement.executeQuery();

        System.out.println("Pasajeros del coche con ID " + idCoche + ":");

        if (resultSet != null && resultSet.next()) {
            do {
                String nombrePasajero = resultSet.getString(SchemaDB.COL_NAM_PAS);
                int idPasajeroCoche = resultSet.getInt("id_coche");
                System.out.println("Pasajero: " + nombrePasajero + ", ID del coche: " + idPasajeroCoche);
            } while (resultSet.next());
        } else {
            System.out.println("No hay pasajeros asociados a este coche.");
        }
    }

    //2.2 creo un metodo para listar los pasajeros y ahorrar codigo
    ArrayList<Pasajeros> getPasajeros (ResultSet datosresultantes) throws SQLException {
        ArrayList<Pasajeros> pasajero = new ArrayList<>();
        while(datosresultantes.next()){
            int id = resultSet.getInt(SchemaDB.COL_ID_PAS);
            String nombre = resultSet.getString(SchemaDB.COL_NAM_PAS);
            int edad = resultSet.getInt(SchemaDB.COL_AGE_PAS);
            int peso = resultSet.getInt(SchemaDB.COL_PES_PAS);
            int id_coche = resultSet.getInt(SchemaDB.COL_ID_COC_PAS);

            pasajero.add(mapearPasajeros(id, nombre, edad, peso, id_coche));

        }return pasajero;
    }

    //2.1 creo un metodo para mapear pasajeros
    public Pasajeros mapearPasajeros(int id, String nombre, int edad, int peso, int id_coche){
        return new Pasajeros(id, nombre, edad, peso, id_coche);
    }
}
