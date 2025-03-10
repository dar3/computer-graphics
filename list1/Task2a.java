package list1;

import java.awt.image.BufferedImage;

public class Task2a {

    public static void main(String[] args) {

        BufferedImage image;

        image = Utils.loadImage(args[0]);

        int imageWidth = 0;
        int imageHeight = 0;

        if (image != null) {
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
        } else {
            System.out.println("Image can't be loaded. Check the path.");
        }


        int centerX = imageWidth / 2;
        int centerY = imageHeight / 2;

        int blackColor = Utils.int2RGB(0, 0, 0);

//        ring width
        int w = 30;

        for (int i = 0; i < imageHeight; i++)
            for (int j = 0; j < imageWidth; j++) {

                double d = Math.sqrt(Math.pow(j - centerX, 2) + Math.pow(i - centerY, 2));
//                Calculating ring index
                int r = (int) d / w;

                // Make decision on the pixel color according to ring index

                if (r % 2 == 0)
                    image.setRGB(j, i, blackColor);
            }

        String savePath = "..\\results\\task2a.bmp";
        Utils.saveImage(image, savePath);

        //command for cmd
        // java .\Task2a.java ..\images\antenna.jpg


    }

}
