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

        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++) {

                double d = Math.sqrt(Math.pow(j - centerX, 2) + Math.pow(i - centerY, 2));
                intensity = (int) (128 * (Math.sin((Math.PI * d) / w) + 1));

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