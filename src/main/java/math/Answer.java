package math;

public class Answer {
    private Double a, b, c, corr;
    private Double sko, S;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public Answer(){

    }
    public void printResult(Equation.Function f){
        printFunction(f);
        if (!S.isNaN()) {
            System.out.printf("Мера отклонения : %.3f\n", S);
        }
        if (!sko.isNaN()) {
            System.out.printf("СКО : %.3f\n", sko);
        }
    }
    private void printFunction(Equation.Function f){
        switch (f){
            case LINEAR://линейная
                System.out.printf(ANSI_RED + "Аппроксимирующая функция - линейная : f = " + "%.3fx%+.3f\n" + ANSI_RESET, a, b);
                System.out.printf("Коэффициент корреляции : %.3f\n", corr);
                break;
            case SQUARE://квадратичная
                System.out.printf(ANSI_GREEN + "Аппроксимирующая функция - квадратичная : f = " + "%.3fx²%+.3fx%+.3f\n" + ANSI_RESET, c, b, a);
                break;
            case EXPONENT://экспоненциальная
                if (!a.isNaN() && !b.isNaN()) {
                    System.out.printf(ANSI_YELLOW + "Аппроксимирующая функция - экспоненциальная : f = " + "%.3fe^%.3fx\n" + ANSI_RESET, a, b);
                }
                break;
            case LOGARIFM://логарифмическая
                if (!a.isNaN() && !b.isNaN()) {
                    System.out.printf(ANSI_PURPLE + "Аппроксимирующая функция - логарифмическая : f = " + "%.3flnx%+.3f\n" + ANSI_RESET, a, b);
                }
                break;
            case POWER://степенная
                if (!a.isNaN() && !b.isNaN()) {
                    System.out.printf(ANSI_BLUE + "Аппроксимирующая функция - степенная : f = " + "%.3fx^%.3f\n" + ANSI_RESET, a, b);
                }
                break;
        }
    }
    public void setParameters(int n, double S, double sko, double a, double b, Double c, Double corr){
        this.a = a;
        this.b = b;
        this.c = c;
        this.sko = sko;
        this.S = S;
        this.corr = corr;
    }

    public Double getC() {
        return c;
    }

    public Double getA() {
        return a;
    }

    public Double getB() {
        return b;
    }
}
