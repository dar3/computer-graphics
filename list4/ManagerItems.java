package list4;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ManagerItems {
    public static ShapeItem deserialize(String data) {
        String[] parts = data.split(",");
        if (parts.length < 14) return null;

        Shape shape;
        boolean isCirc;
        String type = parts[0];

        // original position
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double w = Double.parseDouble(parts[3]);
        double h = Double.parseDouble(parts[4]);

        // shape type
        if (type.equals("CIRC")) {
            shape = new Ellipse2D.Double(x, y, w, h);
            isCirc = true;
        } else {
            shape = new Rectangle2D.Double(x, y, w, h);
            isCirc = false;
        }

        // transform matrix
        double[] matrix = new double[6];
        for (int i = 0; i < 6; i++) {
            matrix[i] = Double.parseDouble(parts[5 + i]);
        }
        AffineTransform transform = new AffineTransform(matrix);

        // color
        Color color = new Color(
                Integer.parseInt(parts[11]),
                Integer.parseInt(parts[12]),
                Integer.parseInt(parts[13])
        );

        // result
        ShapeItem item = new ShapeItem(shape, color, isCirc);
        item.setTransform(transform);
        return item;

    }

    public static ImageItem deserializeImage(String data, String baseDir) {
        String[] parts = data.split(",");

        try {
            // original position
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double width = Double.parseDouble(parts[3]);
            double height = Double.parseDouble(parts[4]);

            // transformation matrix
            double[] matrix = new double[6];
            for (int i = 0; i < 6; i++) {
                matrix[i] = Double.parseDouble(parts[5 + i]);
            }
            AffineTransform transform = new AffineTransform(matrix);

            // image path (full idealy)
            String imagePath = parts[11];

            if (!new File(imagePath).isAbsolute())
                imagePath = new File(baseDir, imagePath).getPath();


            BufferedImage image;
            image = ImageIO.read(new File(imagePath));
            if (image == null)
                throw new IOException("Failed to load image");


            // result
            ImageItem item = new ImageItem(image, (int)x, (int)y, (int)width, (int)height, imagePath);
            item.setTransform(transform);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}