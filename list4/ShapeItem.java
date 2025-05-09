package list4;

import java.awt.*;
import java.awt.geom.*;

class ShapeItem extends DrawableItem {
    Shape shape;
    Color color;
    boolean isCirc = false;

    ShapeItem(Shape shape, Color color, boolean isCirc) {
        this.shape = shape;
        this.color = color;
        this.isCirc = isCirc;
        this.transform = new AffineTransform();
        this.originalBounds = shape.getBounds2D();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        Shape transformedShape = transform.createTransformedShape(shape);
        g2d.fill(transformedShape);
    }

    @Override
    public Shape getOriginalShape(){
        return shape;
    }

    public void drawBounds(Graphics2D g2d){
        Shape transformedBounds = transform.createTransformedShape(originalBounds);
        g2d.setColor(Color.red);
        g2d.draw(transformedBounds);
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();

        // type
        if (isCirc)
            sb.append("CIRC,");
        else
            sb.append("RECT,");

        // original position
        sb.append(originalBounds.getX()).append(",");
        sb.append(originalBounds.getY()).append(",");
        sb.append(originalBounds.getWidth()).append(",");
        sb.append(originalBounds.getHeight()).append(",");

        // transformation matrix
        double[] matrix = new double[6];
        transform.getMatrix(matrix);
        for (int i = 0; i < matrix.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(matrix[i]);
        }

        // color
        sb.append(",").append(color.getRed());
        sb.append(",").append(color.getGreen());
        sb.append(",").append(color.getBlue());

        return sb.toString();
    }
}