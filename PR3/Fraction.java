package PR3;

import java.util.Objects;

public class Fraction {
    private int num;
    private int den;

    // (b) Constructor - reduce to lowest terms
    public Fraction(int num, int den) {
        int g = gcd(num, den);
        if (g == 0) {
            g = 1; // guard against num=0 and den=0 both (avoid div by zero)
        }
        this.num = num / g;
        this.den = den / g;

        // keep the sign on the numerator, denominator positive
        if (this.den < 0) {
            this.num = -this.num;
            this.den = -this.den;
        }
    }

    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public int getNum() {
        return num;
    }

    public int getDen() {
        return den;
    }

    // (c) toString()
    @Override
    public String toString() {
        return num + "/" + den;
    }

    // (d) equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction other = (Fraction) obj;
        return this.num == other.num && this.den == other.den;
    }

    // (d) hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}