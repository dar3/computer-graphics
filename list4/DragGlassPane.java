package list4;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class DragGlassPane extends JPanel {
    private BufferedImage draggedImage;
    private Shape draggedShape;
    private AffineTransform transform;
    private int WIDTH = 100;
    private int HEIGHT = 100;

    public DragGlassPane() {
        setOpaque(false);
        transform = new AffineTransform();
    }

    public void setImage(BufferedImage image, Point loc) {

        draggedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = draggedImage.createGraphics();
        g2d.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2d.dispose();

        transform.setToTranslation(loc.x - WIDTH/2, loc.y - HEIGHT/2);
        repaint();
    }


    public void setShape(Shape shape, Point loc) {
        draggedShape = shape;
        transform.setToTranslation(loc.x - WIDTH/2, loc.y - HEIGHT/2);
        repaint();
    }

    public void updateLocation(Point loc) {
        if (draggedImage != null) {
            int w = draggedImage.getWidth();
            int h = draggedImage.getHeight();
            transform.setToTranslation(loc.x - w /2, loc.y - h / 2);
        } else if (draggedShape != null) {
            Rectangle2D bounds = draggedShape.getBounds2D();

            double centerX = bounds.getCenterX();
            double centerY = bounds.getCenterY();

            transform.setToTranslation(loc.x - centerX, loc.y - centerY);
        }
        repaint();
    }

    public void clearImage() {
        draggedImage = null;
        repaint();
    }

    public void clearShape() {
        draggedShape = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        float alpha = 0.7f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (draggedImage != null && transform != null) {
            g2d.drawImage(draggedImage, transform, this);
        } else if (draggedShape != null && transform != null) {
            g2d.setColor(Color.BLACK);
            g2d.fill(transform.createTransformedShape(draggedShape));
        }

        g2d.dispose();
    }

}