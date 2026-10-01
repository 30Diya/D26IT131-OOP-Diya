package DiscountApp.src.discount;

import java.util.List;

public class DiscountEngine {

    public static void applyDiscount(
            List<Double> prices,
            DiscountRule rule) {

        for (double price : prices) {

            double newPrice = rule.apply(price);

            System.out.println(
                "Old Price: " + price +
                " New Price: " + newPrice
            );
        }
    }
}