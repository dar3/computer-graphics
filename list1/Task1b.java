package list1;


import java.awt.image.BufferedImage;

public class Task1b {

    public static void main(String[] args) {

        // Parametry z wiersza poleceń
        int gridWidth = Integer.parseInt(args[0]);      //grid line width
        int gridSpaceX = Integer.parseInt(args[1]);   // spacing in X
        int gridSpaceY = Integer.parseInt(args[2]);   // spacing in Y

        // Grid and background color
        int gridR = Integer.parseInt(args[3]);
        int gridG = Integer.parseInt(args[4]);
        int gridB = Integer.parseInt(args[5]);
        int bgR = Integer.parseInt(args[6]);
        int bgG = Integer.parseInt(args[7]);
        int bgB = Integer.parseInt(args[8]);


        int width = 500;
        int height = 500;


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        int gridColor = Utils.int2RGB(gridR, gridG, gridB);
        int bgColor = Utils.int2RGB(bgR, bgG, bgB);

        //Background filling
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i, bgColor);
            }
        }

        //start drawing grid
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

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


        String savePath = "..\\results\\task1b.bmp";
        Utils.saveImage(image, savePath);

//        command to run in cmd
//        need to be in list1 folder to run it
//        java Task1b.java 8 20 20 0 0 0 255 255 255
    }
}

