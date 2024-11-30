import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Concesionario {
    private ArrayList<Coche> coches;

    public Concesionario() {
        coches = new ArrayList<>();
        cargarCochesDesdeArchivo();
    }

    private void cargarCochesDesdeArchivo() {
        File ficheroCoches = new File("src/coches.dat");
        ObjectInputStream objectInputStream = null;

        if (ficheroCoches.exists()) {
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(ficheroCoches));
                while (true) {
                    try {
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
        File ficheroCoche = new File("src/coches.dat");
        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(ficheroCoche));
            for (Coche coche : coches) {
                objectOutputStream.writeObject(coche);
            }
        } catch (IOException e) {
            System.out.println("Error al poner el archivo en modo escritura");
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("Error al cerrar el archivo");
            }
        }
    }

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
                case 1:
                    System.out.println("Vamos a introducir un nuevo coche");
                    insertarCoche();
                    break;

                case 2:
                    borrarCoche();
                    break;

                case 3:
                    buscarCoche();
                    break;

                case 4:
                    System.out.println("A continuación, te muestro la lista de coches guardados en el fichero: ");
                    System.out.println("---");
                    listarCoches();
                    break;

                case 5:
                    exportarAcsv();
                    break;

                case 6:
                    exportarArchivo();
                    System.out.println("Cambios guardados en el archivo coches.dat");
                    System.out.println("Cerrando programa");
                default:
                    break;

            }
        } while (opcion != 6);
    }

    public void insertarCoche() {
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

        Coche nuevoCoche = new Coche(id, matricula, marca, modelo, color);

        addCoche(nuevoCoche);
    }

    public void addCoche(Coche coche) {
        coches.add(coche);
        System.out.println("Coche añadido correctamente");

        listarCoches();
    }

    public void listarCoches() {
        System.out.println("Lista de coches:");
        for (Coche coche : coches) {
            System.out.println(coche);
        }
    }

    public void borrarCoche() {

        Scanner scLeer = new Scanner(System.in);
        String matricula;

        do {
            System.out.println("Estos son los vehículos que tengo: ");
            listarCoches();

            System.out.print("Introduce la matrícula del coche que quieres borrar (o 'salir' para cancelar): ");
            matricula = scLeer.nextLine();

            if (matricula.equalsIgnoreCase("salir")) {
                break;
            }

            boolean cocheEliminado = false;
            for (int i = coches.size() - 1; i >= 0; i--) {
                Coche coche = coches.get(i);
                if (coche.getMatricula().equalsIgnoreCase(matricula)) {
                    coches.remove(i);
                    cocheEliminado = true;
                    break; // Salir del bucle una vez encontrado y eliminado
                }
            }

            if (cocheEliminado) {
                exportarArchivo();
                System.out.println("Coche eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún coche con esa matrícula.");
            }
        } while (true);
    }

    public void buscarCoche() {
        Scanner scLeer = new Scanner(System.in);

        System.out.println("Lista de IDs de coches:");
        for (Coche coche : coches) {
            System.out.println("ID: " + coche.getId());
        }

        System.out.println("¿Que coche quieres consultar? Dame el id: ");
        int id = scLeer.nextInt();

        Coche cocheEncontrado = consultarCoche(id);
        consultarCoche(id);
        if (cocheEncontrado != null) {
            System.out.println("Datos del coche:");
            System.out.println(cocheEncontrado); // Suponiendo que la clase Coche tiene un método toString()
        } else {
            System.out.println("No se ha encontrado ningún coche con ese ID.");
        }
    }

    public Coche consultarCoche(int id) {
        for (Coche coche : coches) {
            if (coche.getId() == id) {
                return coche;
            }
        }
        return null;
    }

    public void exportarAcsv() {
        File ficheroCsv = new File("src/coches.csv");
        File ficheroDat = new File("src/coches.dat");
        ObjectInputStream objectInputStream = null;
        PrintWriter printWriter = null;

        if (ficheroCsv.exists()) {
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(ficheroDat));
                printWriter = new PrintWriter(new FileWriter(ficheroCsv));
                printWriter.println("id;matricula;marca;modelo;color");
                while (true) {
                    try {
                        Coche coche = (Coche) objectInputStream.readObject();
                        StringBuilder copiaCompleta = new StringBuilder();
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

            } catch (IOException e) {
                System.out.println("Error al escribir el fichero csv");
            } finally {
                try {
                    objectInputStream.close();
                    printWriter.close();
                } catch (IOException | NullPointerException e) {
                    System.out.println("Error al cerrar la lectura");
                }
            }
        } else {
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