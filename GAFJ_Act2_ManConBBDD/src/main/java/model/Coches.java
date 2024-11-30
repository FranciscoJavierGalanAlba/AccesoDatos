package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Coches {
    private int id;
    private String matricula, marca, modelo, color;

    //como quiero dar un coche sin id, creo un contructor

    public Coches(String matricula, String marca, String modelo, String color){
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
    }


    public void mostrarId(){
        System.out.println("id = " + id );
    }

}
