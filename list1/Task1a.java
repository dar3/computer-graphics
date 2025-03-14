package list1;

import java.awt.image.BufferedImage;
import java.lang.Math;

public class Task1a {

    public static void main(String[] args) {

        BufferedImage image;

        //image size
        int width = 500;
        int height = 500;

        // Create an empty image
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fill it with a gray-shaded pattern
        int color;
        int intensity;
        int i, j;

        //rings width
        int w = 10;


        int centerX = width / 2;
        int centerY = height / 2;

//        i stands for Y axis (pixel rows)
//        j stands for X axis (pixel columns)
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++) {

//                Euclidean distance
                double d = Math.sqrt(Math.pow(j - centerX, 2) + Math.pow(i - centerY, 2));
//                sin (-1 1) +1 makes (0,2)
//                128 * makes intensity from (0, 255)
//                we can put 127.99 instead of 128 so we wouldn't need to use second if
                intensity = (int) (128 * (Math.sin((Math.PI * d) / w) + 1));

//                limitations so it stays in the rbg limit
                if (intensity < 0) {
                    intensity = 0;
                }
                if (intensity > 255) {
                    intensity = 255;
                }

                color = Utils.int2RGB(intensity, intensity, intensity);

                // IN order to read R,G,B from a pixel
                image.setRGB(j, i, color);

                System.out.println(d);


            }
        String savePath = ".\\results\\task1a.bmp";
        Utils.saveImage(image, savePath);
    }
}