package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConecction {
    private Connection connection;

    // 1. Creo un metodo para dar la conexion (punto de conexion)
    public Connection getConnection(){
        if(connection==null){
            newConnection();
        }
        return connection;
    }

    // 2. Si no he podido dar la conexion porque no existe, la creo:
    private void newConnection(){
        String url = "jdbc:mysql://localhost/" + SchemaDB.DB_NAME;

        try {
            //creo una conexion a partir de la url
            connection = DriverManager.getConnection(url, "root", "");
        } catch (SQLException e) {
            System.out.println("Error en la conexion a la base de datos");
        }
        System.out.println("Conexion creada correctamente");
    }

    //3. ahora necesito otro metodo para cerrar la conexion
    private void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion");
        } finally {
            connection = null;
        }
    }
}
