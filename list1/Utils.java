package list1;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class Utils {

    // Method assembles RGB color intensities into single
    // packed integer. Arguments must be in <0..255> range
    static int int2RGB(int red, int green, int blue)
    {
        // Make sure that color intensities are in 0..255 range
        red = red & 0x000000FF;
        green = green & 0x000000FF;
        blue = blue & 0x000000FF;

        // assembling using bit shifting
        return (red << 16) + (green << 8) + blue;
    }

    static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            return null;
        }
    }

    static void saveImage(BufferedImage image, String path) {
        try
        {
            ImageIO.write(image, "bmp", new File(path));
            System.out.println("Image created successfully");
        }
        catch (IOException e)
        {
            System.out.println("The image cannot be stored");
        }
    }
}