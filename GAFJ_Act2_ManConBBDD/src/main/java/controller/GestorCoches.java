package controller;

import com.mysql.cj.protocol.Resultset;
import dao.CochesDAO;
import dao.PasajerosDAO;
import database.SchemaDB;
import model.Coches;
import model.Pasajeros;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorCoches {
    private CochesDAO cochedao;
    private PasajerosDAO pasajerosDAO;

    //1. inicializo cochedao
    public GestorCoches(){
        cochedao = new CochesDAO();
        pasajerosDAO = new PasajerosDAO();
    }

    //2. Creo menu:
    public void mostrarMenu(){
        Scanner scLeer = new Scanner(System.in);
        int opcion = 0;

        do{
            System.out.println("-----------");
            System.out.println("Bienvenido. ¿Que quieres hacer?");
            System.out.println("1.Añadir un nuevo coche. ");
            System.out.println("2.Borrar un coche. ");
            System.out.println("3.Consultar un coche por id.");
            System.out.println("4.Modificar un coche por id.");
            System.out.println("5.Listado de coches");
            System.out.println("6.Gestión de pasajeros.");
            System.out.println("7.Terminar el programa. ");
            System.out.println("Introduce la opcion que deseas:  ");

            opcion = scLeer.nextInt();

            switch (opcion){
                case 1:
                    insertarCoche();
                    break;
                case 2:
                    borrarCoche();
                    break;
                case 3:
                    consultarCocheId();
                    break;
                case 4:
                    modificaCoche();
                    break;
                case 5:
                    listarCoches();
                    break;
                case 6:
                    subMenu();
                default:
                    System.out.println("Saliendo del programa");
                    break;
            }

        } while (opcion != 7);
    }

    // 3. metodo para insertar coches
    public void insertarCoche(){
        Scanner scLeer = new Scanner(System.in);

        //Pido datos del coche
        System.out.println("Introduce la matricula del coche: ");
        String matricula = scLeer.nextLine();

        System.out.println("Introduce la marca del coche: ");
        String marca = scLeer.nextLine();

        System.out.println("Introduce el modelo del coche: ");
        String modelo = scLeer.nextLine();

        System.out.println("Introduce el color del coche: ");
        String color = scLeer.nextLine();

        try {
            //llamo al metodo addCoche de la clase Coche dao y añado un nuevo coche
            cochedao.addCoche(new Coches(matricula, marca, modelo, color));
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al insertar un nuevo coche");
        }
        System.out.println("Coche agregado");

    }

    //4. metodo para listar coches
    public void listarCoches() {
        try {
            //Creo una lista para almacenar los coches
            ArrayList<Coches> listaCoches = cochedao.obtenerCoches();

            //Recorro la lista para imprimir los coches
            for (Coches coche : listaCoches) {
                System.out.println(coche);
            }
        } catch (SQLException |NullPointerException e) {
            System.out.println("Error en la conexión");
        }
    }

    //4.1 metodo para listar coches por id
    public void listarCochesId() {
        try {
            //Creo una lista para almacenar los coches
            ArrayList<Integer> listaCoches = cochedao.obtenerCochesId();

            //Recorro la lista para imprimir los coches
            for (Integer coche : listaCoches) {
                System.out.println(coche);
            }
        } catch (SQLException |NullPointerException e) {
            System.out.println("Error en la conexión" + e.getMessage());
        }
    }

    //5. metodo para borrar coches
    public void borrarCoche(){
        Scanner scLeer = new Scanner(System.in);
        int id = 0;

        // primero listo los coches que hay guardados
        listarCoches();

        //ahora pido el id del coche a borrar
        System.out.println("Introduce el id del coche a borrar: ");
        id = scLeer.nextInt();

        ArrayList<Coches> listaCoches = null;
        try {
            listaCoches = cochedao.obtenerCoches();

            for (Coches coches : listaCoches){
                if(coches.getId() == id){
                    cochedao.eliminaCocheId(id);
                    System.out.println("Coche eliminado");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexion a la base de datos" + e.getMessage());
        }

    }

    //6. metodo para buscar coche por id
    public void consultarCocheId(){
        Scanner scLeer = new Scanner(System.in);
        int id = 0;

        //muestro los ids disponibles
        System.out.println("Lista de coches: ");
        listarCochesId();

        System.out.println("Introduce el id del coche que quieres ver: ");
        id = scLeer.nextInt();

        try {
            //llamo al metodo obtenerCoches y almaceno en la variable tipo lista los coches
            ArrayList<Coches> listaCoches = cochedao.obtenerCoches();
            for (Coches coches : listaCoches){
                //si hay coche con ese id, me imprimes el coche
                if (coches.getId() == id){
                    System.out.println("ID: " + coches.getId());
                    System.out.println("Matrícula: " + coches.getMatricula());
                    System.out.println("Marca: " + coches.getMarca());
                    System.out.println("Modelo: " + coches.getModelo());
                    System.out.println("Color: " + coches.getColor());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexion de la base de datos.");
        }
    }

    //7. actualizar datos del coche
    public void modificaCoche(){
        Scanner scLeer = new Scanner(System.in);
        int id = 0;

        // primero listo los coches que hay guardados
        listarCoches();

        //ahora pido el id del coche a actualizar
        System.out.println("Introduce el id del coche a actualizar: ");
        id = scLeer.nextInt();

        //creo una lista vacia
        ArrayList<Coches> listaCoches = null;
        try {
            listaCoches = cochedao.obtenerCoches();

            for (Coches coches : listaCoches){
                if(coches.getId() == id){

                    System.out.println("Introduce la nueva matrícula: ");
                    String nuevaMatricula = scLeer.next();

                    System.out.println("Introduce la nueva marca: ");
                    String nuevaMarca = scLeer.next();

                    System.out.println("Introduce el nuevo modelo: ");
                    String nuevoModelo = scLeer.next();

                    System.out.println("Introduce el nuevo color: ");
                    String nuevoColor = scLeer.next();

                    // Llamo al método modificarCoches con los nuevos valores
                    cochedao.modificarCoches(id, nuevaMatricula, nuevaMarca, nuevoModelo, nuevoColor);
                    System.out.println("Coche modificado");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexion a la base de datos" + e.getMessage());
        }
    }


    //PASAJEROS
    //1. metodo submenu de pasajeros
    public void subMenu(){
        Scanner scLeer = new Scanner(System.in);
        int opcion = 0;
        int idPas = 0;
        int idCoc = 0;

        do{
            System.out.println("Bienvenido al submenu de pasajeros. Opciones: ");
            System.out.println("1.Añadir nuevo pasajero. ");
            System.out.println("2.Borrar pasajero por id. ");
            System.out.println("3.Consultar pasajero por id. ");
            System.out.println("4.Listar todos los pasajeros. ");
            System.out.println("5.Añadir pasajero al coche.");
            System.out.println("6.Eliminar pasajero al coche. ");
            System.out.println("7.Lista de todos los pasajeros de un coche. ");
            System.out.println("8.Salir.");
            System.out.println("Introduce la opcion que deseas:  ");
            opcion = scLeer.nextInt();

            switch (opcion){
                case 1:
                    insertarPasajero();
                    break;
                case 2:
                    eliminarPasajero();
                    break;
                case 3:
                    consultarPasajeroId();
                    break;
                case 4:
                    listarPasajeros();
                    break;
                case 5:
                    addPasajeroCoche();
                    break;
                case 6:
                    eliminarPasajeroCoche();
                    break;
                case 7:
                    listarPasajeroCoche();
                    break;
                default:
                    break;
            }
        } while (opcion!=8);
    }

    //2. Insertar pasajeros
    public void insertarPasajero(){
        Scanner scLeer = new Scanner(System.in);


        System.out.println("Dime el nombre del pasajero: ");
        String nombre = scLeer.next();

        System.out.println("Dime la edad del pasajero: ");
        int edad = scLeer.nextInt();

        System.out.println("Dime el peso del pasajero: ");
        int peso = scLeer.nextInt();

        System.out.println("Dime el ID del coche al que asignarás al pasajero (0 si no tiene coche asignado): ");
        int id_coche = scLeer.nextInt();

        try {
            pasajerosDAO.addPasajero(new Pasajeros(nombre, edad, peso, id_coche));
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error en la conexion a la base de datos" + e.getMessage());
        }

    }

    // Metodo para listar pasajeros
    public void listarPasajeros(){
        try {
            ArrayList<Pasajeros> listaPasajeros = pasajerosDAO.obtenerPasajeros();

            for(Pasajeros pasajeros : listaPasajeros){
                System.out.println(pasajeros);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error en la conexion a la base de datos. " + e.getMessage());
        }
    }

    public void listarPasajeroId(){
        try {
            ArrayList<Integer> listaPasajeros = pasajerosDAO.obtenerPasajeroId();

            for (Integer pasajeros : listaPasajeros){
                System.out.println(pasajeros);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error en la conexion a la base de datos" + e.getMessage());
        }

    }

    //Metodo para borrar pasajeros
    public void eliminarPasajero(){
        Scanner scanner = new Scanner(System.in);
        int id = 0;

        listarPasajeros();

        System.out.println("Dime el id del pasajero que quieres eliminar: ");
        id = scanner.nextInt();

        //creo una lista para almacenar todos los pasajeros
        ArrayList<Pasajeros> listaPasajero = null;
        try {
            //almaceno en la lista los pasajeros
            listaPasajero = pasajerosDAO.obtenerPasajeros();
            //recorro la lista
            for (Pasajeros pasajero : listaPasajero){
                if(pasajero.getId() == id){
                    pasajerosDAO.eliminarPasajeroId(id);
                    System.out.println("Pasajero eliminado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //metodo para consultar pasajeros por id
    public void consultarPasajeroId(){
        Scanner scLeer = new Scanner(System.in);
        int id = 0;

        System.out.println("Lista de pasajeros: ");
        listarPasajeroId();

        System.out.println("Introduce el id del pasajero que quieres ver: ");
        id = scLeer.nextInt();

        try {
            ArrayList<Pasajeros> listaPasajeros = pasajerosDAO.obtenerPasajeros();
            for (Pasajeros pasajeros : listaPasajeros){
                if(pasajeros.getId() == id){
                    System.out.println("Id: " + pasajeros.getId());
                    System.out.println("Nombre: " + pasajeros.getNombre());
                    System.out.println("Edad: " + pasajeros.getEdad());
                    System.out.println("Peso: " + pasajeros.getPeso());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexion a la base de datos");
        }
    }

    //metodo para añadir pasajeros a un coche
    public void addPasajeroCoche(){
        Scanner scLeer = new Scanner(System.in);
        int idPas = 0;
        int idCoc = 0;


        System.out.println("Coches disponibles: ");
        listarCoches();

        System.out.println("-----");
        System.out.println("Pasajeros: ");
        listarPasajeros();

        System.out.println("------");
        System.out.println("Dime el id del pasajero: ");
        idPas = scLeer.nextInt();

        System.out.println("Dime el id del coche: ");
        idCoc = scLeer.nextInt();

        try {
            pasajerosDAO.insertarPasajeroCoche(idCoc, idPas);
        } catch (SQLException e) {
            System.out.println("Error en la conexion a la base de datos." + e.getMessage());
        }

        listarPasajeros();

    }

    //metodo para eliminar pasajeros de un coche
    public void eliminarPasajeroCoche(){
        Scanner scLeer = new Scanner(System.in);
        int idPas = 0;
        int idCoc = 0;

        System.out.println("Coches disponibles: ");
        listarCoches();

        System.out.println("-----");
        System.out.println("Pasajeros: ");
        listarPasajeros();

        System.out.println("------");
        System.out.println("Dime el id del pasajero: ");
        idPas = scLeer.nextInt();

        System.out.println("Dime el id del coche  ");
        idCoc = scLeer.nextInt();

        try {
            pasajerosDAO.deletePasajeroCoche(idCoc, idPas);
        } catch (SQLException e) {
            System.out.println("Error en la conexion a la base de datos." + e.getMessage());
        }
    }

    //metodo para listar pasajeros de un coche
    public void listarPasajeroCoche(){
        Scanner scLeer = new Scanner(System.in);
        int id = 0;

        System.out.println("Lista de coches: ");
        listarCoches();

        System.out.println("Dame el id del coche: ");
        id = scLeer.nextInt();

        try {
            pasajerosDAO.listarPasajerosDeCoche(id);
        } catch (SQLException e) {
            System.out.println("Error en la conexion " + e.getMessage() );
        }
    }

    public static void main(String[] args) {
        GestorCoches gestorCoches = new GestorCoches();
        gestorCoches.mostrarMenu();
    }

}
