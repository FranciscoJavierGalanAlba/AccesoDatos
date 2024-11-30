package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Pasajeros {
    private int id;
    private String nombre;
    private int edad, peso, id_coche;

    public Pasajeros(String nombre, int edad, int peso, int id_coche) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.id_coche = id_coche;
    }
}
