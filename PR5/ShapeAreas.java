package PR5;
import java.util.Scanner;

abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }
}

class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }
}

public class ShapeAreas {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("How many shapes do you want to enter? ");
        int n = sc.nextInt();

        Shape[] shapes = new Shape[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nShape " + (i + 1));
            System.out.println("1. Circle");
            System.out.println("2. Rectangle");
            System.out.println("3. Triangle");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter radius: ");
                    double radius = sc.nextDouble();

                    shapes[i] = new Circle(radius);
                    break;

                case 2:
                    System.out.print("Enter width: ");
                    double width = sc.nextDouble();

                    System.out.print("Enter height: ");
                    double height = sc.nextDouble();

                    shapes[i] = new Rectangle(width, height);
                    break;

                case 3:
                    System.out.print("Enter base: ");
                    double base = sc.nextDouble();

                    System.out.print("Enter height: ");
                    double triangleHeight = sc.nextDouble();

                    shapes[i] = new Triangle(base, triangleHeight);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    i--; // Ask again for this shape
            }
        }

        double total = 0;
        Shape largest = null;
        double largestArea = 0;

        System.out.println("\n--- Shape Areas ---");

        // One loop using polymorphism
        for (Shape shape : shapes) {

            double area = shape.area();

            System.out.printf(
                "%s area = %.2f%n",
                shape.getClass().getSimpleName(),
                area
            );

            total += area;

            if (largest == null || area > largestArea) {
                largest = shape;
                largestArea = area;
            }
        }

        System.out.printf("%nTotal area = %.2f%n", total);

        System.out.printf(
            "Largest shape = %s with area = %.2f%n",
            largest.getClass().getSimpleName(),
            largestArea
        );

        sc.close();
    }
}