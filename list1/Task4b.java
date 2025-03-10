package list1;

import java.awt.image.BufferedImage;

public class Task4b {

    public static void main(String[] args) {

        BufferedImage image1, image2;

        image1 = Utils.loadImage(".\\images\\antenna.jpg");
        image2 = Utils.loadImage(".\\images\\winter.jpg");

        int firstImageWidth;
        int firstImageHeight;

        int gridWidth = 8;
        int gridSpaceX = 20;
        int gridSpaceY = 20;


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

//                        //vertical lines
//                        if (j % (gridSpaceX + gridWidth) < gridWidth) {
//                            image2.setRGB(j, i, image1.getRGB(j,i));
//                        }
//
//                        //horizontal lines
//                        if (i % (gridSpaceY + gridWidth) < gridWidth) {
//                            image2.setRGB(j, i, image1.getRGB(j,i));
//                        }

                        // Checking if pixel is on the line
                        boolean isPixelInVerticalLine = j % (gridSpaceX + gridWidth) < gridWidth;
                        boolean isPixelInHorizontalLine = i % (gridSpaceY + gridWidth) < gridWidth;

                        if (isPixelInVerticalLine || isPixelInHorizontalLine) {
                            // if pixel is on the line use image1
                            image2.setRGB(j, i, image1.getRGB(j, i));
                        }
                        // if not, just leave image2
                    }
                }

                String savePath = ".\\results\\task4b.bmp";
                Utils.saveImage(image2, savePath);
            }
        }

    }
}
