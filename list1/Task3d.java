package list1;

import java.awt.image.BufferedImage;

public class Task3d {

    public static void main(String[] args) {


        int imageWidth = 500;
        int imageHeight = 500;

        int elem_size = 100;
        int center_x = elem_size / 2;
        int center_y = elem_size / 2;

        int ring_width = 8;

        int whiteColor = Utils.int2RGB(255,255,255);
        int blackColor = Utils.int2RGB(0,0,0);

// Fill raster matrix pixel by pixel
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imageHeight; i++)
            for (int j = 0; j < imageWidth; j++) {
                int is = i % elem_size;
                int js = j % elem_size;
                center_x = elem_size / 2;
                center_y = elem_size / 2;
                double dist_to_center = (is - center_y) * (is - center_y) +
                        (js - center_x) * (js - center_x);
                dist_to_center = Math.sqrt(dist_to_center);
                int ring_index = (int)(dist_to_center / ring_width);

                int color;
                if (ring_index % 2 == 0)
                    color = blackColor;
                else
                    color = whiteColor;

                image.setRGB(j, i, color);
            }

        String savePath = ".\\results\\task3d.bmp";
        Utils.saveImage(image, savePath);

    }
}
