package math;

import math.Answer;
import swing.Graphic;

import javax.swing.*;
import java.util.Arrays;

/*
Для исследования использовать:
a) линейную функцию;
b) полиномиальную функцию 2-й степени;
c) экспоненциальную функцию;
d) логарифмическую функцию
f) степенную функцию.
 */
public class Func {

    private static double[] x;
    private static double[] y;
    private static int n;
    private double a, b;
    Double c = null, corr = null;
    private Equation.Function function, loopF;
    private final Equation[] equation  = new Equation[5];
    private double S = 0, sko, minSKO = Double.MAX_VALUE;

    public Func(double[] x, double[] y) {
        Func.x = x.clone();
        Func.y = y.clone();
        n = x.length;
    }

    public void getApproximation() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        Answer answer = new Answer();
        Answer all = new Answer();

        int k = 0;
        for (Equation.Function f : Equation.Function.values()){

            loopF = f;
            solve();
            if (sko < minSKO) {
                function = f;
                minSKO = sko;
                answer.setParameters(n, S, sko, a, b, c, corr);
            }
            all.setParameters(n, S, sko, a, b, c, corr);
            equation[k] = new Equation();
            equation[k].setA(all.getA());
            equation[k].setB(all.getB());
            equation[k].setC(all.getC());
            equation[k].setFunction(f);
            k++;
            all.printResult(f);
        }
        Graphic graphic = new Graphic();
        graphic.setEquation(equation);
        graphic.gui();
        System.out.println("------------------------------------");
        System.out.println("Наилучшая аппроксимирующая функция:");
        answer.printResult(function);
    }

    private void solve() {
        switch (loopF) {
            case LINEAR:
                linear();
                break;
            case SQUARE:
                square();
                break;
            case EXPONENT:
                exponent();
                break;
            case LOGARIFM:
                logarifm();
                break;
            case POWER:
                power();
                break;
        }
    }

    public void linear() {//f = ax + b
        double SX = 0, SXX = 0, SY = 0, SXY = 0;
        S = 0;
        corr = 0.0;
        double sum1 = 0, sum2 = 0, sum3 = 0;
        for (int i = 0; i < n; i++) {
            SX += x[i];
            SXX += x[i] * x[i];
            SY += y[i];
            SXY += x[i] * y[i];
        }
        double delta = SXX * n - SX * SX;
        a = (SXY * n - SX * SY) / delta;
        b = (SXX * SY - SX * SXY) / delta;
        for (int i = 0; i < n; i++) {
            S += Math.pow(a * x[i] + b - y[i], 2);
            sum1 += (x[i] - SX / n) * (y[i] - SY / n);
            sum2 += Math.pow(x[i] - SX / n , 2);
            sum3 += Math.pow(y[i] - SY / n , 2);
        }
        sko = Math.sqrt(S / n);
        corr = sum1 / (Math.sqrt(sum2 * sum3));
    }

    public void square() {//f = a + bx + cx^2
        S = 0;
        double SX = 0, SX2 = 0, SY = 0, SXY = 0, SX3 = 0, SX4 = 0, SXY2 = 0;
        for (int i = 0; i < n; i++) {
            SX += x[i];
            SX2 += Math.pow(x[i], 2);
            SX3 += Math.pow(x[i], 3);
            SX4 += Math.pow(x[i], 4);
            SY += y[i];
            SXY += x[i] * y[i];
            SXY2 += x[i] * x[i] * y[i];
        }
        double delta = n * SX2 * SX4 + 2 * SX * SX3 * SX2 - Math.pow(SX2, 3) - n * SX3 * SX3 - SX * SX * SX4;
        a = (SY * SX2 * SX4 + SX * SX3 * SXY2 + SXY * SX2 * SX3 - SX2 * SX2 * SXY2 - SX3 * SX3 * SY - SX * SXY * SX4) / delta;
        b = (n * SX4 * SXY + SY * SX3 * SX2 + SX * SXY2 * SX2 - SXY * SX2 * SX2 - n * SX3 * SXY2 - SX * SY * SX4) / delta;
        c = (n * SX2 * SXY2 + SX * SX2 * SXY + SX * SX3 * SY - SY * SX2 * SX2 - n * SX3 * SXY - SX * SX * SXY2) / delta;
        for (int i = 0; i < n; i++) {
            S += Math.pow(a + b * x[i] + c * Math.pow(x[i], 2) - y[i], 2);
        }
        sko = Math.sqrt(S / n);
    }

    public void exponent() {//f = ae^(bx)
        double SX = 0, SXX = 0, SY = 0, SXY = 0;
        S = 0;
        for (int i = 0; i < n; i++) {
            SX += x[i];
            SXX += x[i] * x[i];
            SY += Math.log(y[i]);
            SXY += x[i] * Math.log(y[i]);
        }
        double delta = SXX * n - SX * SX;
        b = (SXY * n - SX * SY) / delta;
        a = (SXX * SY - SX * SXY) / delta;
        a = Math.exp(a);
        for (int i = 0; i < n; i++) {
            S += Math.pow(a * Math.exp(x[i] * b) - y[i], 2);
        }
        sko = Math.sqrt(S / n);
    }

    public void logarifm() {//f = alnx + b
        double SX = 0, SXX = 0, SY = 0, SXY = 0;
        S = 0;
        for (int i = 0; i < n; i++) {
            SX += Math.log(x[i]);
            SXX += Math.log(x[i]) * Math.log(x[i]);
            SY += y[i];
            SXY += y[i] * Math.log(x[i]);
        }
        double delta = SXX * n - SX * SX;
        a = (SXY * n - SX * SY) / delta;
        b = (SXX * SY - SX * SXY) / delta;
        for (int i = 0; i < n; i++) {
            S += Math.pow(a * Math.log(x[i]) + b - y[i], 2);
        }
        sko = Math.sqrt(S / n);
    }

    public void power() { //f = ax^b
        double SX = 0, SXX = 0, SY = 0, SXY = 0;
        S = 0;
        for (int i = 0; i < n; i++) {
            SX += Math.log(x[i]);
            SXX += Math.log(x[i]) * Math.log(x[i]);
            SY += Math.log(y[i]);
            SXY += Math.log(x[i]) * Math.log(y[i]);
        }
        double delta = SXX * n - SX * SX;
        b = (SXY * n - SX * SY) / delta;
        a = (SXX * SY - SX * SXY) / delta;
        a = Math.exp(a);
        for (int i = 0; i < n; i++) {
            S += Math.pow(a * Math.pow(x[i], b) - y[i], 2);
        }
        sko = Math.sqrt(S / n);
    }
    public static double getMaxX(){
        double max = Double.MIN_VALUE;
        for (double v : x) {
            max = Math.max(max, v);
        }
        return max;
    }
    public static double getMinX(){
        double min = Double.MAX_VALUE;
        for (double v : x) {
            min = Math.min(min, v);
        }
        return min;
    }
    public static double getMaxY(){
        double max = Double.MIN_VALUE;
        for (double v : y) {
            max = Math.max(max, v);
        }
        return max;
    }
    public static double getMinY(){
        double min = Double.MAX_VALUE;
        for (double v : y) {
            min = Math.min(min, v);
        }
        return min;
    }

    public static double[] getX() {
        return x;
    }
    public static double[] getY(){
        return y;
    }
    public static int getN() {
        return n;
    }
}
