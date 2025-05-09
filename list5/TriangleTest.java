package tests.gouradmar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*;

public class TriangleTest extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int NUM_TRIANGLES = 1000;

    private Triangle2D[] triangles;
    private BufferedImage bufferedImage;


    public TriangleTest() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        generateTriangles();
    }

    private void generateTriangles() {
        Random rand = new Random();
        triangles = new Triangle2D[NUM_TRIANGLES];
        for (int i = 0; i < NUM_TRIANGLES; i++) {
            Point[] pts = new Point[3];
            Color[] cols = new Color[3];
            for (int j = 0; j < 3; j++) {
                pts[j] = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
                cols[j] = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            }
            triangles[i] = new Triangle2D(pts, cols);
        }
    }

    //    for rendering, works like a benchmark
    private void render(Graphics g) {
        long startTime, endTime;
        double totalTime ;

        // render to bufferedImage
        startTime = System.nanoTime();
        for (Triangle2D t : triangles) {
            t.drawTriangleIncr(bufferedImage);
        }
        g.drawImage(bufferedImage, 0, 0, null);

        endTime = System.nanoTime();
        totalTime = (endTime - startTime) / 1e9;
        System.out.printf("Buffered image: %.2f triangles/s, %.2f shaded pixels/s\n",NUM_TRIANGLES / totalTime, countTotalPixels() / totalTime);

        // render directly to graphics
        startTime = System.nanoTime();
        for (Triangle2D t : triangles) {
            t.drawTriangleIncr(g);
        }
        endTime = System.nanoTime();

        totalTime = (endTime - startTime) / 1e9;
        System.out.printf("Direct draw to Graphics: %.2f triangles/s, %.2f shaded pixels/s\n", NUM_TRIANGLES / totalTime, countTotalPixels() / totalTime);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private int countTotalPixels() {
        int pixels = 0;
        for (Triangle2D t : triangles) {
            pixels += t.getPixels();
        }
        return pixels;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Triangle Shading Test");
        TriangleTest panel = new TriangleTest();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}