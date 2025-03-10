package list1;

import java.awt.image.BufferedImage;

public class Task3c {

    public static void main(String[] args) {

        int imageWidth = 500;
        int imageHeight = 500;

        int blackColor = Utils.int2RGB(0, 0, 0);
        int whiteColor = Utils.int2RGB(255, 255, 255);
        int color;

        // Calculating image center coordinates
        int centerX = imageWidth / 2;
        int CenterY = imageHeight / 2;


        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);


        for (int i = 0; i < imageHeight; i++)
            for (int j = 0; j < imageWidth; j++) {

                int w = 1;

                int r = 0;

                double d = Math.sqrt(Math.pow(j - centerX, 2) + Math.pow(i - CenterY, 2));

               // Adding 1px of width for each ring
                while(d > 0)
                {
                    // reducing the distance by moving to the next ring
                    d = d - w;

                    // increase the ring number
                    r++;
                    if (r % 2 == 0)

                     // if even increase width by 1
                        w += 1;
                }


                if (r % 2 == 0) {
                    color = blackColor;
                } else {
                    color = whiteColor;
                }

                // IN order to read R,G,B from a pixel
                image.setRGB(j, i, color);
            }
        String savePath = "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\task3c.bmp";
        Utils.saveImage(image, savePath);
    }
}
