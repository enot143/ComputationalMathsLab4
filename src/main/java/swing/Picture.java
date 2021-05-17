package swing;

import lombok.SneakyThrows;
import math.Equation;
import math.Func;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

public class Picture extends JComponent {

    static double mX, mY;
    double x = 0;
    double y = 0;
    Equation[] equation;

    public Picture(Equation[] equation) {
        this.equation = equation;
    }

    @SneakyThrows
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        double panelWidth = d.getWidth();
        double panelHeight = d.getHeight();
        double maxX;
        double maxY;
        maxX = 1.5 * Math.abs(-Func.getMinX() + Func.getMaxX());
        maxY = 1.5 * Math.abs(-Func.getMinY() + Func.getMaxY());
        //масштабирование
        mX = panelWidth / maxX;
        mY = panelHeight / maxY;
        double m = Math.min(mX, mY);
        //смещение
        double kY = panelHeight - 50;
        double kX = 50;
        //оси
        Graphics2D g2 = (Graphics2D) g;
        Point2D x1 = new Point2D.Double(0, kY);
        Point2D x2 = new Point2D.Double(panelWidth, kY);
        Line2D xOs = new Line2D.Double(x1, x2);
        g2.setPaint(Color.black);
        g2.draw(xOs);
        Point2D y1 = new Point2D.Double(kX, 0);
        Point2D y2 = new Point2D.Double(kX, panelHeight);
        Line2D yOs = new Line2D.Double(y1, y2);
        g2.setPaint(Color.black);
        g2.draw(yOs);
        //подписи осей
        g2.drawString("x", (float) panelWidth - 30, (float) (kY + 10));
        g2.drawString("y", (float) (kX + 10), (float) (30));


        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        //точки
        for (int i = 0; i < Func.getX().length; i++) {
//            String resultX = decimalFormat.format(Func.getX()[i]);
//            String resultY = decimalFormat.format(Func.getY()[i]);
            Ellipse2D point = new Ellipse2D.Double(Func.getX()[i] * m + kX - 2.5- Func.getMinX() * m, kY - Func.getY()[i] * m - 2.5 + Func.getMinY() * m, 5, 5);
            g2.draw(point);
            g2.fill(point);
//            g2.drawString("(" + resultX + "," + resultY + ")", (float) (Func.getX()[i] * m + kX - Func.getMinX() * m), (float) (kY + 15 - Func.getY()[i] * m + Func.getMinY() * m));
        }

        //графикИ
        int c = 0;
        Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.PINK, Color.YELLOW};
        for (Equation e : equation) {
            x = Func.getMinX();
            g2.setColor(colors[c]);
            c++;
            while (x <= panelWidth) {//1.5 * maxX
                x += 0.001;
                y = f(x, e);
                Point2D p1 = new Point2D.Double(x * m + kX - Func.getMinX() * m, kY - y * m + Func.getMinY() * m);
                x += 0.001;
                y = f(x, e);
                Point2D p3 = new Point2D.Double(x * m + kX - Func.getMinX() * m, kY - y * m + Func.getMinY() * m);
                Line2D n1 = new Line2D.Double(p1, p3);
                g2.draw(n1);
            }
        }
        //единичные отрезки
        x = 0;
        String xStr = decimalFormat.format(Func.getMinX());
        g2.setPaint(Color.BLACK);
        int k = 0;
        int del = 0;
        while (x < 1.5 * maxX * m) {//1.5
            Point2D p1 = new Point2D.Double(x + kX, kY - 5);
            Point2D p3 = new Point2D.Double(x + kX, kY + 5);
            Line2D n1 = new Line2D.Double(p1, p3);
            Font osi = new Font("Comic Sans MS", Font.PLAIN, 10);
            g2.setFont(osi);
            if (x >= del * panelWidth/m) {
                del++;
                g2.drawString(xStr, (float) (x + kX - 5), (float) (kY + 15));
            }
            x += m;
            k++;
            xStr = decimalFormat.format(Func.getMinX() + k);
            g2.draw(n1);
        }
        y = 0;
        k = 0;
        del = 0;
        xStr = decimalFormat.format(Func.getMinY());
        while (y < 1.5 * maxY * m) {//1.5
            Point2D p1 = new Point2D.Double(kX - 5, kY - y);
            Point2D p3 = new Point2D.Double(kX + 5, kY - y);
            Line2D n1 = new Line2D.Double(p1, p3);
            if (y >= del * panelHeight/m) {
                del++;
                g2.drawString(xStr, (float) (kX - 35), (float) (kY - y + 5));
            }
            y += m;
            k++;
            xStr = decimalFormat.format(Func.getMinY() + k);
            g2.draw(n1);
        }
        validate();
    }

    private double f(double x, Equation equation) {
        return equation.getFunction().f(equation.getA(), equation.getB(), equation.getC(), x);
    }
}
