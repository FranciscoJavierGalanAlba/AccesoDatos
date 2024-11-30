package model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString


public class Alumnos implements Serializable {
    private double rating;
    private int age;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private int calificaation;
    private String higher_grade;

    public Alumnos(double rating, int age, String name,
                   String gender, String email, String phone,
                   int calificaation, String higher_grade) {
        this.rating = rating;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.calificaation = calificaation;
        this.higher_grade = higher_grade;
    }

    public void mostrarDatos() {
        System.out.println("rating = " + rating);
        System.out.println("age = " + age);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("email = " + email);
        System.out.println("phone = " + phone);
        System.out.println("calificaation = " + calificaation);
        System.out.println("higher_grade = " + higher_grade);
    }
}
