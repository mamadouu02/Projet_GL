package maths;

import maths.MathsDeca;

public class TestMaths {
    public static void main(String[] args) {
        System.out.println("TestMaths");
        System.out.println("MathsDeca.pi = " + MathsDeca.pi);

        System.out.println("MathsDeca.arctan(0) = " + MathsDeca.arctan(0));
        System.out.println("MathsDeca.arctan(1) = " + MathsDeca.arctan(1));
        System.out.println("MathsDeca.arctan(1) = " + MathsDeca.arctan(1.1f));
        System.out.println("MathsDeca.arctan(2) = " + MathsDeca.arctan(2));
        System.out.println("MathsDeca.arctan(3pi) = " + MathsDeca.arctan(3 * MathsDeca.pi));

    }
}
