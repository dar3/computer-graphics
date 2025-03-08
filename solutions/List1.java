package solutions;

import java.io.*;
import java.util.Random;
import java.awt.image.*;
import java.awt.Color;
import javax.imageio.*;

public class List1 {

    private static final int IMAGE_SIZE = 2000;

    public static void main(String[] args) {
        System.out.println("Program just started");

        BufferedImage grayImage = createGrayImage(IMAGE_SIZE, IMAGE_SIZE);
        saveImage(grayImage, "bmp", "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\out_img_gray.bmp", "Gray");

        BufferedImage colorImage = createColorImage(IMAGE_SIZE, IMAGE_SIZE);
        saveImage(colorImage, "jpg", "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\out_img_color.jpg", "Color");

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
                System.out.println(d1);
                System.out.println(d);

                int intensity = (int) (128 * (Math.sin((Math.PI * d1) / w) + 1));
                intensity = clamp(intensity, 0, 255);

                int color = new Color(intensity, intensity, intensity).getRGB();
                image.setRGB(j, i, color);
            }
        }
        return image;
    }

    // Tworzenie kolorowego obrazu
    private static BufferedImage createColorImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int gray = j % 256;
                int color = byte2RGB(gray, (256 - gray), (i % 256));
                image.setRGB(j, i, color);
            }
        }
        return image;
    }

    // Zapisywanie do pliku
    private static void saveImage(BufferedImage image, String format, String filePath, String imageType) {
        try {
            ImageIO.write(image, format, new File(filePath));
            System.out.println(imageType + " image created successfully");
        } catch (IOException e) {
            System.out.println(imageType + " image cannot be stored in " + format.toUpperCase() + " file");
        }
    }

    // Konwersja wartości bajtów na kolor RGB
    private static int byte2RGB(int red, int green, int blue) {
        red = clamp(red, 0, 255);
        green = clamp(green, 0, 255);
        blue = clamp(blue, 0, 255);
        return (red << 16) + (green << 8) + blue;
    }

    // Ograniczenie zakresu wartości (0-255)
    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
