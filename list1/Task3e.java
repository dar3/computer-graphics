package list1;

import java.awt.image.BufferedImage;

public class Task3e {

    public static void main(String[] args) {


        int imageWidth = 500;
        int imageHeight = 500;

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        int centerX = imageWidth / 2;
        int centerY = imageHeight / 2;

        int whiteColor = Utils.int2RGB(255, 255, 255);
        int blackColor = Utils.int2RGB(0, 0, 0);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {

                // Calculating point between point (x, y) and center
                // atan2 returns the angle in radians of the polar coordinates
                double angle = Math.atan2(y - centerY, x - centerX);

                // if angle is negative add 2*PI so it becomes from the first quarter (positive)
                if (angle < 0) {
                    angle += 2 * Math.PI;
                }

                // Coloring stripe based on angle  % 2 == 0 even or odd
                // Math.PI/15 because we have 15 stripes
                if (Math.floor(angle / (Math.PI / 15)) % 2 == 0) {
                    image.setRGB(x, y, blackColor);
                } else {
                    image.setRGB(x, y, whiteColor);
                }
            }
        }

        String savePath = ".\\results\\task3e.bmp";
        Utils.saveImage(image, savePath);
    }
}


