package list1;

import java.awt.image.BufferedImage;

public class Task4c {

    public static void main(String[] args) {

        BufferedImage image1, image2;

        image1 = Utils.loadImage(".\\images\\antenna.jpg");
        image2 = Utils.loadImage(".\\images\\winter.jpg");

        int firstImageWidth;
        int firstImageHeight;
        int fieldSize = 30;

        if (image1 == null || image2 == null) {
            System.out.println("Images can't be null. Please provide the images");

        } else {
            firstImageWidth = image1.getWidth();
            firstImageHeight = image1.getHeight();

            if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
                System.out.println("Images are different sizes. Please make them the same size.");
            } else {

                for (int i = 0; i < firstImageHeight; i++) {
                    for (int j = 0; j < firstImageWidth; j++) {

                        if (((j / fieldSize) + (i / fieldSize)) % 2 == 0) {
                            image1.setRGB(j, i, image2.getRGB(j, i));
                        }
                    }

                }

                String savePath = ".\\results\\task4c.bmp";
                Utils.saveImage(image1, savePath);
            }
        }
    }
}
