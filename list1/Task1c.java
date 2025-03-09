package list1;


import java.awt.image.BufferedImage;

public class Task1c {

    public static void main(String[] args) {

        if (args.length < 7) {
            System.out.println("Użycie: java Task1b <rozmiar obrazu> <wielkość pola> <kolor1 R> <kolor1 G> <kolor1 B> <kolor2 R> <kolor2 G> <kolor2 B> <ścieżka zapisu>");
            return;
        }

        int width = 500;
        int height = 500;

        int fieldSize = Integer.parseInt(args[0]);       // field size

//        first tile color
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
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {

                if (((x / fieldSize) + (y / fieldSize)) % 2 == 0) {
                    image.setRGB(x, y, color1);
                } else {
                    image.setRGB(x, y, color2);
                }
            }
        }


        String savePath = "D:\\Documents\\STUDIA\\PWr_SUBJECTS\\SEMESTR_6\\grafika_laby\\created_images\\task1c.bmp";
        Utils.saveImage(image, savePath);

//        for cmd
//        java Task1c.java 50 0 0 0 255 255 255
    }
}
