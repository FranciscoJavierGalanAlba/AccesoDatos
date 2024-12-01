import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Concesionario2 {
    //lista de coches donde almaceno los coches del concesionario
    private ArrayList<Coche> coches;

    public Concesionario2() {
        //inicializo la lista coche, es decir, creo una lista vacia para poder ir añadiendo coches
        coches = new ArrayList<>();
        cargarCochesDesdeArchivo();
    }

    //con este metodo compruebo si existe un coche con ese id y matricula
    private boolean duplicado(int id, String matricula){
        //recorro la lista coches
        for (Coche coche: coches){
            //si hay un coche con el mismo id y misma matricula
            if(coche.getId() == id || coche.getMatricula().equalsIgnoreCase(matricula)){
                //devuelve true
                return true;
            }
            //devuelve falso
        } return false;
    }

    //metodo para cargar coches desde una archivo binario
    private void cargarCochesDesdeArchivo() {
        //creo un fichero para representar el archivo coches
        File ficheroCoches = new File("src/coches.dat");
        //utilizo object para leer el archivo
        ObjectInputStream objectInputStream = null;

        //si existe:
        if (ficheroCoches.exists()) {
            try {
                //preparo el fichero para la entrada/escritura de datos
                objectInputStream = new ObjectInputStream(new FileInputStream(ficheroCoches));
                while (true) {
                    try {
                        //leo el coche
                        Coche coche = (Coche) objectInputStream.readObject();
                        coches.add(coche); // Agregamos el coche a la lista existente
                    } catch (EOFException e) {
                        // Fin del archivo
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al leer el fichero de coches: " + e.getMessage());
            } finally {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    System.out.println("Error al cerral el archivo en modo lectura");
                }
            }
        }
    }
    // Método para exportar coches a CSV
    public void exportarArchivo() {
        //creo un fichero para representar el archivo coches.dat
        File ficheroCoche = new File ("src/coches.dat");
        //utilizo object para la salida/lectura del fichero
        ObjectOutputStream objectOutputStream = null;

        try {
            //preparo el fichero para la lectura
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(ficheroCoche));
            //recorro la lista coches
            for (Coche coche: coches){
                //escribo en la lista los coches del fichero
                objectOutputStream.writeObject(coche);
            }
        } catch (IOException e) {
            System.out.println("Error al poner el archivo en modo escritura");
        } finally{
            try {
                objectOutputStream.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("Error al cerrar el archivo");
            }
        }
    }

    //este metodo será el menu de usuario
    public void mostrarMenu() {
        Scanner scLeer = new Scanner(System.in);
        int opcion = 0;
        do {

            System.out.println("-----------");
            System.out.println("Bienvenido a MiConcesionario. ¿Que quieres hacer?");
            System.out.println("1.Añadir un nuevo coche. ");
            System.out.println("2.Borrar un coche. ");
            System.out.println("3.Consultar un coche por id.");
            System.out.println("4.Listado de coches. ");
            System.out.println("5.Exportar a CSV");
            System.out.println("6.Terminar el programa. ");
            System.out.println("Introduce la opcion que deseas:  ");
            opcion = scLeer.nextInt();

            switch (opcion) {
                //si pulsa 1, introduce nuevo coche
                case 1:
                    System.out.println("Vamos a introducir un nuevo coche");
                    insertarCoche();
                    break;

                //si pulsa 2, borra coche
                case 2:
                    borrarCoche();
                    break;

                //si pulsa 3, busca coche
                case 3:
                    buscarCoche();
                    break;

                //si pulsa 4, muestra los coches que hay en el fichero
                case 4:
                    System.out.println("A continuación, te muestro la lista de coches guardados en el fichero: ");
                    System.out.println("---");
                    listarCoches();
                    break;

                //si pulsa 5, exporta el fichero a .csv
                case 5:
                    exportarAcsv();
                    break;

                default:
                //si pulsa 6, guarda cambios el fichero .dat
                    exportarArchivo();
                    System.out.println("Cambios guardados en el archivo coches.dat");
                    System.out.println("Cerrando programa");
                    break;
            }
        }while (opcion != 6) ;
    }

    //metodo para insertar coches, se pediran por los datos del coche
    public void insertarCoche(){
        Scanner scLeer = new Scanner(System.in);

        System.out.println("Vas a introducir un coche: ");
        System.out.println("-----");

        System.out.println("Introduce el id: ");
        int id = scLeer.nextInt();

        System.out.println("Introduce la matricula: ");
        String matricula = scLeer.next();

        System.out.println("Introduce la marca: ");
        String marca = scLeer.next();

        System.out.println("Introduce el modelo: ");
        String modelo = scLeer.next();

        System.out.println("Introduce el color: ");
        String color = scLeer.next();

        //si el metodo duplicado devuelve true, dice que ya existe coche con ese id y matricula
        if(duplicado(id, matricula)){
            System.out.println("Error, el id o la matricula existe. Prueba otra vez.");
            return;
        }

        //creo un nuevo coche con los datos introducidos
        Coche nuevoCoche = new Coche(id, matricula, marca, modelo, color);

        //añado el coche creado al metodo addCoche
        addCoche(nuevoCoche);
    }

    //metodo para añadir coches, necesitare un coche
    public void addCoche(Coche coche) {
        //añado el coche a la lista
        coches.add(coche);
        System.out.println("Coche añadido correctamente");

        //muestro los coches de la lista
        listarCoches();
    }

    //metodo para listar coches
    public void listarCoches() {
        System.out.println("Lista de coches:");
        //recorre la lista coches y los imrprime
        for (Coche coche : coches) {
            System.out.println(coche);
        }
    }

    //metodo para borrar coches
    public void borrarCoche() {
        Scanner scLeer = new Scanner(System.in);
        String matricula;

        do {
            //muestro la lista de coches
            System.out.println("Estos son los vehículos que tengo: ");
            listarCoches();

            System.out.print("Introduce la matrícula del coche que quieres borrar (o 'salir' para cancelar): ");
            matricula = scLeer.nextLine();

            if (matricula.equalsIgnoreCase("salir")) {
                break;
            }

            // creo un boolean para que me diga si ha borrado coche o no
            boolean cocheEliminado = false;
            //recorro la lista coches
            for (int i = coches.size() - 1; i >= 0; i--) {
                //obtengo cada coche en la posicion i
                Coche coche = coches.get(i);
                //si hay coche con la misma matricula
                if (coche.getMatricula().equalsIgnoreCase(matricula)) {
                    //lo borra
                    coches.remove(i);
                    cocheEliminado = true;
                    break; // Salir del bucle una vez encontrado y eliminado
                }
            }

            //si el coche es eliminado, exporta la lista
            if (cocheEliminado) {
                exportarArchivo();
                System.out.println("Coche eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún coche con esa matrícula.");
            }
        } while (true);
    }

    //metodo para buscar coches
    public void buscarCoche(){
        Scanner scLeer = new Scanner(System.in);

        //muestro los id de los coches que hay
        System.out.println("Lista de IDs de coches:");
        for (Coche coche : coches) {
            System.out.println("ID: " + coche.getId());
        }

        System.out.println("¿Que coche quieres consultar? Dame el id: ");
        int id = scLeer.nextInt();

        //creo una variable tipo coche y se le asigna el resultado de llamar al metodo
        //consultarcoche y pasarle el valor id coomo parametro
        Coche cocheEncontrado = consultarCoche(id);
        consultarCoche(id);
        if (cocheEncontrado != null) {
            System.out.println("Datos del coche:");
            System.out.println(cocheEncontrado); // Suponiendo que la clase Coche tiene un método toString()
        } else {
            System.out.println("No se ha encontrado ningún coche con ese ID.");
        }
    }

    //metodo que pide un id y muestra los coches con ese id
    public Coche consultarCoche(int id) {
        for (Coche coche: coches){
            if (coche.getId()==id){
                return coche;
            }
        }
        return null;
    }

    //metodo  para exportar el fichero .dat a fichero.csv
    public void exportarAcsv() {
        //creo fichero para salida a .csv
        File ficheroCsv = new File("src/coches.csv");
        //creo fichero para entrada .dat
        File ficheroDat = new File("src/coches.dat");

        ObjectInputStream objectInputStream = null;
        PrintWriter printWriter = null;

        if(ficheroCsv.exists()){
            try{
                //creo flujo de entrada (preparo para lectura)
                objectInputStream = new ObjectInputStream(new FileInputStream(ficheroDat));
                //creo un flujo de salida (preparo para la escritura)
                printWriter = new PrintWriter(new FileWriter(ficheroCsv));
                //escribe la primera linea del fichero .csv
                printWriter.println("id;matricula;marca;modelo;color");
                while (true) {
                    try {
                        //almaceno en la variable el coche del archivo.dat
                        Coche coche = (Coche) objectInputStream.readObject();
                        //stringbuilder para construir la linea
                        StringBuilder copiaCompleta = new StringBuilder();
                        //agrega los datos a la linea
                        copiaCompleta.append(coche.getId()).append(";")
                                .append(coche.getMatricula()).append(";")
                                .append(coche.getMarca()).append(";")
                                .append(coche.getModelo()).append(";")
                                .append(coche.getColor());
                        printWriter.println(copiaCompleta.toString());
                    } catch (EOFException e) {
                        // Fin del archivo
                        break;
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error al leer o escribir el archivo: " + e.getMessage());
                    }
                }

            } catch (IOException e){
                System.out.println("Error al escribir el fichero csv");
            } finally {
                try {
                    objectInputStream.close();
                    printWriter.close();
                } catch (IOException |NullPointerException e) {
                    System.out.println("Error al cerrar la lectura");
                }
            }
        }else {
            try {
                objectInputStream.close();
                printWriter.close();

            } catch (IOException e) {
                System.out.println("Error al cerrar los archivos: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Concesionario2 coche = new Concesionario2();
                coche.mostrarMenu();
    }
}
