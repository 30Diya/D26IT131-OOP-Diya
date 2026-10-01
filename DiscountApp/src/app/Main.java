package DiscountApp.src.app;


import discount.DiscountEngine;
import discount.DiscountRule;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Double> prices = Arrays.asList(
            500.0,
            1000.0,
            1500.0
        );

        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.println("3. Rs.100 Discount");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        DiscountRule rule;

        if (choice == 1) {

            rule = price -> price * 0.90;

        } else if (choice == 2) {

            rule = price -> price * 0.80;

        } else if (choice == 3) {

            rule = price -> price - 100;

        } else {

            System.out.println("Wrong choice");
            return;
        }

        DiscountEngine.applyDiscount(prices, rule);

        sc.close();
    }
}
