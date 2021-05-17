package math;


public class Equation {

    private double a, b;
    private Double c = null;
    private Function function;
    public Equation(){
    }
    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Double getC() {
        if (c == null){
            return 0.0;
        }
        return c;
    }

    public enum Function {
        LINEAR {
            public double f(double a, double b, double c, double x) {
                return a * x + b;
            }
        },
        SQUARE {
            public double f(double a, double b, double c, double x) {
                return a + b * x + c * x * x;
            }
        },
        POWER {
            public double f(double a, double b, double c, double x) {
                return a * Math.pow(x, b);
            }
        },
        LOGARIFM {
            public double f(double a, double b, double c, double x) {
                return a * Math.log(x) + b;
            }
        },
        EXPONENT {
            public double f(double a, double b, double c, double x) {
                return a * Math.exp(b * x);
            }
        };
        public abstract double f(double a, double b, double c, double x);
    }
}
