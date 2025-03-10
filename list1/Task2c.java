package list1;

import java.awt.image.BufferedImage;

public class Task2c {

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

        // fixed tile size
        int fieldSize = 30;


        int color1 = Utils.int2RGB(0, 0, 0);


        // chess field generation
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {

                if (((x / fieldSize) + (y / fieldSize)) % 2 == 0) {
                    image.setRGB(x, y, color1);
                }
            }

        }

//        String savePath = "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\task2c.bmp";
        String savePath = "..\\results\\task2c.bmp";
        Utils.saveImage(image, savePath);

//        java .\Task2c.java ..\images\antenna.jpg

    }
}
