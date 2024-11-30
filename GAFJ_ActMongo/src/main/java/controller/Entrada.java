package controller;

import dao.AlumnosDAO;
import dao.ProfesoresDAO;
import model.Alumnos;
import model.Profesores;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Entrada {
    AlumnosDAO alumnosDAO = new AlumnosDAO();
    ProfesoresDAO profesoresDAO = new ProfesoresDAO();

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do{
            System.out.println("Bienvenido al sistema. Opciones: ");
            System.out.println("1. Insertar profesor: ");
            System.out.println("2. Insertar alumno: ");
            System.out.println("3. Mostrar datos"); //mostrara datos prosores y alumnos
            System.out.println("4. Mostrar profesores"); //mostrara datos solo profesores
            System.out.println("5. Mostrar alumnos"); //mostrara datos solo alumnos
            System.out.println("6. Buscar alumnos"); //se pedirá email del alumnos
            System.out.println("7. Buscar profesor"); // se pedirá rango de edad
            System.out.println("8. Actualizar profesor"); //se pedirá email profesor y actualiza calificación
            System.out.println("9. Dar de baja alumnos"); // borrar alumnos cuya calificación es inferior a 5
            System.out.println("10. Salir");
            opcion = scanner.nextInt();

            switch (opcion){
                case 1:
                    insertarProfesor();
                    break;
                case 2:
                    insertarAlumno();
                    break;
                case 3:
                    mostrarDatos();
                    break;
                case 4:
                    System.out.println("Datos de profesores: ");
                    mostrarProfesores();
                    break;
                case 5:
                    System.out.println("Datos de alumnos: ");
                    mostrarAlumnos();
                    break;
                case 6:
                    dameAlumnosMail();
                    break;
                case 7:
                    dameProfesorEdad();
                    break;
                case 8:
                    actualizaProfe();
                    break;
                case 9:
                    eliminarAlum();
                    break;
                default:
                    break;
            }

        } while(opcion != 10);

    }

    public void insertarProfesor(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Dime el puntuaje de clasificacion: ");
        double rating = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Dime la edad del profesor: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Dime el nombre del profesor: ");
        String nombre = scanner.nextLine();

        System.out.println("Dime el sexo del profesor: ");
        String gender = scanner.nextLine();

        System.out.println("Dime el email del profesor: ");
        String email = scanner.nextLine();

        System.out.println("Dime el telefono del profesor: ");
        String phone = scanner.nextLine();


        System.out.println("Dime las asignaturas que imparte (separadas por comas): ");
        String subjectsString = scanner.nextLine();
        String[] subjectArray = subjectsString.split(",");
        List<String> subjectsList = Arrays.asList(subjectArray);

        System.out.println("Dime el titulo que tiene: ");
        String title = scanner.next();

        profesoresDAO.insertarProfesores(new Profesores(rating, age, nombre,
                gender, email, phone, subjectsList, title));


        System.out.println("El profesor ha sido añadido");
    }

    public void insertarAlumno(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Dime el rating: ");
        double rating = scanner.nextDouble();

        System.out.println("Dime la edad del alumno: ");
        int age = scanner.nextInt();

        System.out.println("Dime el nombre del alumno: ");
        String nombre = scanner.next();

        System.out.println("Dime el sexo del alumno: ");
        String gender = scanner.next();

        System.out.println("Dime el email del alumno: ");
        String email = scanner.next();

        System.out.println("Dime el telefono del alumno: ");
        String telefono = scanner.next();

        System.out.println("Dime su calificaation:  ");
        int calificaation = scanner.nextInt();

        System.out.println("Dime el curso del alumno: ");
        String grade = scanner.next();

        alumnosDAO.insertarAlumno(new Alumnos(rating, age, nombre, gender,
                email, telefono, calificaation, grade));

        System.out.println("El alumno ha sido añadido");
    }

    private void mostrarDatos(){
        System.out.println("Mostrando los alumnos: ");
        alumnosDAO.dameAlumnos();

        System.out.println("----------");

        System.out.println("Mostrando los profesores : ");
        profesoresDAO.dameProfesores();
    }

    private void mostrarProfesores(){
        System.out.println("Mostrando los profesores : ");

        profesoresDAO.dameProfesores();
    }

    private void mostrarAlumnos(){
        System.out.println("Mostrando alumnos: ");
        alumnosDAO.dameAlumnos();
    }

    private void dameAlumnosMail(){
        Scanner scanner = new Scanner(System.in);
        String email = null;

        System.out.println("Dime el email del alumno: ");
        email = scanner.next();

        alumnosDAO.dameAlumnosEmail(email);
    }

    private void dameProfesorEdad(){
        Scanner scanner = new Scanner(System.in);
        int edad1 = 0;
        int edad2 = 0;

        System.out.println("¿Entre que edades quieres buscar al profesor?  ");
        System.out.println("Edad menor: ");
        edad1 = scanner.nextInt();
        System.out.println("Edad mayor: ");
        edad2 = scanner.nextInt();

        profesoresDAO.mostarProfesorEdad(edad1, edad2);
    }

    public void actualizaProfe(){
        Scanner scanner = new Scanner(System.in);
        String email= null;
        double calificacion = 0;

        System.out.println("¿cual es el email del profesor que quieres actualizar?");
        email = scanner.nextLine();
        System.out.println("cual es la nueva calificacion?");
        calificacion = scanner.nextDouble();

        profesoresDAO.actualizarCalifiProfe(email, calificacion);
    }

    private void eliminarAlum(){
        alumnosDAO.eliminarAlumno();
        System.out.println("Alumnos con calificacion < 5 borrados.");
    }

    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        entrada.menu();
    }
}
