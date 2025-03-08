package solutions;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.awt.image.*;
import java.awt.Color;
import java.lang.Math;
import javax.imageio.*;


public class List1_old {


    private static Scanner in;

    public static void main(String[] args) {


        System.out.println("Program just started");


        BufferedImage image;

        // Create an empty image
        image = new BufferedImage(2000, 2000,
                BufferedImage.TYPE_INT_RGB);

        // Fill it with a gray-shaded pattern
        int color;
        int gray;
        int i, j;
        double a, f;
        a = 20.0;
        f = 5.0;
        // w - szerokosc pierscieni, czestotliwosc funkcji sinus
        int w = 500;
        int height = image.getHeight();
        int width = image.getWidth();

        color = 0;
        w = width;
        int w1 = 50;
        int ri = 50;
        int ro = 150;
        Random rand = new Random();
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++) {


                // List1 a
                // d1 to odległość danego piksela od środka obrazu
                // i - wysokosc j - szerokosc
                double d1 = Math.sqrt((double) (i - width / 2) * (i - width / 2) + (j - height / 2) * (j - height / 2));
                double d = 0;
                // d1 /= skalowanie obrazu
                d1 /= 1.0;
                d1 += 0.05;
                d = 1.0 / d1;
                ri = ((int) d) / 20;
                System.out.println(d1);
                System.out.println(d);


                int intensity = (int) (128 * (Math.sin((Math.PI * d1) / w) + 1));
                if (intensity < 0)
                    intensity = 0;
                if (intensity > 255)
                    intensity = 255;
                color = new Color(intensity, intensity, intensity).getRGB();


                // IN order to read R,G,B from a pixel
                image.setRGB(j, i, color);


                // IN order to read R,G,B from a pixel
                int in_c = image.getRGB(i, j);
                Color cc_in = new Color(in_c);
                int rc = cc_in.getRed();
                int gc = cc_in.getGreen();
                int bc = cc_in.getBlue();


            }

        // Save image in graphics file
        try {
//            File  dir = new File ( dirname );
//            dir.mkdir();
            ImageIO.write(image, "bmp", new File("D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\out_img_gray.bmp"));
            System.out.println("Gray image created successfully");
        } catch (IOException e) {
            System.out.println("Gray image cannot be stored in BMP file");
        }
        ;

        // Now make color image
        // IMPORTANT: colors second image out_img_color.jpg

        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++) {
                gray = (byte) (j % 256);
                color = byte2RGB(gray, (256 - gray), (i % 256));
                image.setRGB(j, i, color);
            }



        // Save image in graphics file
        try {
            ImageIO.write(image, "jpg", new File("D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\out_img_color.jpg"));
            System.out.println("Color image created successfully");
        } catch (IOException e) {
            System.out.println("Color image cannot be stored in BMP file");
        }

        System.exit(0);
    }

    static int byte2RGB(int red, int green, int blue) {
        // Color components must be in range 0 - 255
        red = 0xff & red;
        green = 0xff & green;
        blue = 0xff & blue;
        return (red << 16) + (green << 8) + blue;
    }

}

