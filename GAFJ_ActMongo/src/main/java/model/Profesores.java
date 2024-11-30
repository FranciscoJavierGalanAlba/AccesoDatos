package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Profesores implements Serializable {
    private double rating;
    private int age;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private List<String> subjects;
    private String title;

    public void mostrarDatos() {
        System.out.println("rating = " + rating);
        System.out.println("age = " + age);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("email = " + email);
        System.out.println("phone = " + phone);
        System.out.println("subject = " + subjects);
        System.out.println("titulo = " + title);
    }


}
