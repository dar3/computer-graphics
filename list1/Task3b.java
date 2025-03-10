package list1;

import java.awt.image.BufferedImage;

public class Task3b {

    public static void main(String[] args) {

        int imageWidth = 500;
        int imageHeight = 500;

        int fieldSize = 50;

        // define colors
        int blackColor = Utils.int2RGB(0, 0, 0);
        int whiteColor = Utils.int2RGB(255, 255, 255);


        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);


        // Calculating pixels
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {

                // Calculating the distance from the left edge of the grid (gx) and from the right edge of the grid
                int gx = Math.min(x % fieldSize, fieldSize - (x % fieldSize));
                // Calculating the distance from the top edge of the grid (gy) and from the bottom edge of the grid
                int gy = Math.min(y % fieldSize, fieldSize - (y % fieldSize));

                if (gy > gx) {
                    image.setRGB(x, y, blackColor);
                } else {
                    image.setRGB(x, y, whiteColor);
                }

            }
        }


        String savePath = "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\task3b.bmp";
        Utils.saveImage(image, savePath);
    }
}
