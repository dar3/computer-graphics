package list1;


import java.awt.image.BufferedImage;

public class Task2b {

    public static void main(String[] args) {

        BufferedImage image;

        image = Utils.loadImage(args[0]);

        int imageWidth = 0;
        int imageHeight = 0;

        int gridWidth = 8;
        int gridSpaceX = 20;
        int gridSpaceY = 20;

        if (image != null) {
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
        } else {
            System.out.println("Image can't be loaded. Check the path.");
        }


        int gridColor = Utils.int2RGB(0, 0, 0);

//start drawing grid
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {

                //vertical lines
                if (j % (gridSpaceX + gridWidth) < gridWidth) {
                    image.setRGB(j, i, gridColor);
                }

                //horizontal lines
                if (i % (gridSpaceY + gridWidth) < gridWidth) {
                    image.setRGB(j, i, gridColor);
                }
            }

        }

        String savePath = "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\task2b.bmp";
        Utils.saveImage(image, savePath);

//        java .\Task2b.java D:\Documents\Programming_projects\ComputerGraphics\images\antenna.jpg


    }

}
