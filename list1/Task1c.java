package list1;


import java.awt.image.BufferedImage;

public class Task1c {

    public static void main(String[] args) {

        int width = 500;
        int height = 500;

        int fieldSize = Integer.parseInt(args[0]);       // field size

//        first tile color
        // (0,0,0) - black
        // (255,255,255) - white
        int r1 = Integer.parseInt(args[1]);
        int g1 = Integer.parseInt(args[2]);
        int b1 = Integer.parseInt(args[3]);

//        second tile color
        int r2 = Integer.parseInt(args[4]);
        int g2 = Integer.parseInt(args[5]);
        int b2 = Integer.parseInt(args[6]);


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        int color1 = Utils.int2RGB(r1, g1, b1);
        int color2 = Utils.int2RGB(r2, g2, b2);

        // chess field generation
        // y vertical x horizontal
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {

                // sum represents the shift
                //Divides the x coordinate by the fieldSize value (e.g. 50).
                // The effect is to get the horizontal "column" (field) number.
                // endless chess and we only use window of chess table
                if (((x / fieldSize) + (y / fieldSize)) % 2 == 0) {
                    image.setRGB(x, y, color1);
                } else {
                    image.setRGB(x, y, color2);
                }
            }
        }


        String savePath = "..\\results\\task1c.bmp";
        Utils.saveImage(image, savePath);

//        for cmd
//        java Task1c.java 50 0 0 0 255 255 255
    }
}
