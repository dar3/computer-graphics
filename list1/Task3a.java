package list1;

import java.awt.image.BufferedImage;

public class Task3a {

    public static void main(String[] args) {

        BufferedImage image;

        // image size
        int imageWidth = 500;
        int imageHeight = 500;

        // circle radius
        int r = 25;

        // space between circles
        int d = 60;

        // colors
        int blackColor = Utils.int2RGB(0, 0, 0);
        int greyColor = Utils.int2RGB(109, 115, 111);


        // Create an empty image
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        // finding image center
        int centerX;
        int centerY;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {

                image.setRGB(x, y, greyColor);
            }
        }

        /*
        // circles that are fully show on the image

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {

                // Calculating if pixels is inside of circle
                for (centerY = d / 2; centerY < imageHeight; centerY += d) {
                    for (centerX = d / 2; centerX < imageWidth; centerX += d) {
                        int dx = x - centerX;
                        int dy = y - centerY;
                        if (dx * dx + dy * dy <= r * r) {
                            image.setRGB(x, y, blackColor);
                        }
                    }
                }

            }
        }
*/

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {

                // Calculating if pixels is inside of circle
                for (centerY = 0; centerY <= imageHeight + d / 2; centerY += d) {
                    for (centerX = 0; centerX <= imageWidth + d / 2; centerX += d) {
                        //calculating pixel distance from circle center
                        int dx = x - centerX;
                        int dy = y - centerY;

                        // checking if pixel is inside the circle
                        // circle equation (x-xa)^2 + (y-ya)^2 <= r^2
                        if (Math.pow(dx,2) + Math.pow(dy, 2) <= Math.pow(r,2)) {
                            image.setRGB(x, y, blackColor);
                        }
                    }
                }
            }
        }

        String savePath = "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\task3a.bmp";
        Utils.saveImage(image, savePath);

    }
}

