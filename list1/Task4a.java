package list1;

import java.awt.image.BufferedImage;

public class Task4a {

    public static void main(String[] args) {

        BufferedImage image1, image2;

        image1 = Utils.loadImage(".\\images\\antenna.jpg");
        image2 = Utils.loadImage(".\\images\\winter.jpg");

        int firstImageWidth;
        int firstImageHeight;
        int w = 30;



        if (image1 == null && image2 == null) {
            System.out.println("Images can't be null. Please provide the images");

        } else {
            firstImageWidth = image1.getWidth();
            firstImageHeight = image1.getHeight();

            if (image1.getWidth() != image2.getWidth() && image1.getHeight() != image2.getHeight()) {
                System.out.println("Images are different sizes. Please make them the same size.");
            } else {
                int centerX = firstImageWidth / 2;
                int centerY = firstImageHeight / 2;

                for (int i = 0; i < firstImageHeight; i++) {
                    for (int j = 0; j < firstImageWidth; j++) {
                        double d = Math.sqrt(Math.pow(j - centerX, 2) + Math.pow(i - centerY, 2));
                        int r = (int) d / w;

                        if (r % 2 == 0)
                            image1.setRGB(j, i, image2.getRGB(j,i));
                    }
                }
            }

            String savePath = ".\\results\\task4a.bmp";
            Utils.saveImage(image1, savePath);
        }

    }

}
