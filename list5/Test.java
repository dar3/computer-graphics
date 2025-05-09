package tests.gouradmar;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Test extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Triangle2D triangle;
    private BufferedImage bufferedImage;


    public Test() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        generateTriangles();

    }

    private void generateTriangles() {
//        Point[] pts = {
//                new Point(100,100),
//                new Point(500, 500),
//                new Point(550, 250)
//        };

// opposite triangle verticle down
//        Point[] pts = {
//                new Point(400, 500),
//                new Point(350, 100),
//                new Point(450, 100)
//
//        };

// rozwartokatny
//        Point[] pts = {
//                new Point(100, 100),
//                new Point(700, 150),
//                new Point(400, 500)
//
//        };

// very small
        Point[] pts = {
                new Point(200, 200),
                new Point(400, 400),
                new Point(300, 201)

        };

        Color[] cols = {
                Color.RED,
                Color.blue,
                Color.yellow
        };

        triangle = new Triangle2D(pts, cols);
    }



    private void render(Graphics g) {
        // render to bufferedImage
        triangle.drawTriangleIncr(bufferedImage);
        g.drawImage(bufferedImage, 0, 0, null);

        // render directly to graphics
//        triangle.drawTriangleIncr(g);
    }

    public void setVerticesAndColors(Point[] pts, Color[] cols) {
        triangle = new Triangle2D(pts, cols);
        repaint();
    }


    //    painting graphic component
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Triangle Shading Test");
        Test panel = new Test();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Point[] pts = {
                new Point(200, 100),
                new Point(600, 500),
                new Point(300, 400)
        };
        Color[] cols = {
                Color.GREEN,
                Color.MAGENTA,
                Color.CYAN
        };
        panel.setVerticesAndColors(pts, cols);

    }
}