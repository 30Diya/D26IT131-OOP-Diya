package PR11;

import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    int age;
    transient String password;

    Student(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public String toString() {
        return "Name: " + name +
               ", Age: " + age +
               ", Password: " + password;
    }
}

public class SaveLoadDemo {
    public static void main(String[] args) {

        Student[] students = {
            new Student("Rahul", 20, "abc123"),
            new Student("Priya", 21, "xyz789"),
            new Student("Amit", 19, "pass456")
        };

        // Save objects to file
        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                         new FileOutputStream("students.dat"))) {

            out.writeObject(students);
            System.out.println("Objects saved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load objects from file
        try (ObjectInputStream in =
                     new ObjectInputStream(
                         new FileInputStream("students.dat"))) {

            Student[] loadedStudents = (Student[]) in.readObject();

            System.out.println("\nObjects loaded successfully:");

            for (Student s : loadedStudents) {
                System.out.println(s);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}