package list1;

import java.io.*;
import java.util.Random;
import java.awt.image.*;
import java.awt.Color;
import javax.imageio.*;

public class Task1a_blur {

    private static final int IMAGE_SIZE = 200;

    public static void main(String[] args) {
        System.out.println("Program just started");

        BufferedImage image = createGrayImage(IMAGE_SIZE, IMAGE_SIZE);
        String savePath = ".\\results\\task1a_blur.bmp";
        Utils.saveImage(image, savePath);

//        BufferedImage colorImage = createColorImage(IMAGE_SIZE, IMAGE_SIZE);
//        saveImage(colorImage, "jpg", "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\out_img_color.jpg", "Color");

        System.exit(0);
    }

    // Tworzenie szarego obrazu
    // podpunkt a
    private static BufferedImage createGrayImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int w = width;
        Random rand = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double d1 = Math.sqrt(Math.pow(i - width / 2, 2) + Math.pow(j - height / 2, 2));
                d1 /= 1.0;
                d1 += 0.05;
                double d = 1.0 / d1;
//                System.out.println(d1);
//                System.out.println(d);

                int intensity = (int) (128 * (Math.sin((Math.PI * d1) / w) + 1));
                intensity = clamp(intensity, 0, 255);

                int color = new Color(intensity, intensity, intensity).getRGB();
                image.setRGB(j, i, color);
            }
        }
        return image;
    }


    // Ograniczenie zakresu wartości (0-255)
    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
