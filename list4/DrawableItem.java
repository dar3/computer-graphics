package list4;

import java.awt.*;
import java.awt.geom.*;

abstract class DrawableItem {
    protected AffineTransform transform;
    protected Rectangle2D originalBounds;

    public abstract void draw(Graphics2D g2d);

    public abstract String serialize();

    public Shape getOriginalShape() {
        return originalBounds;
    }

    public void translate(double dx, double dy) {
        AffineTransform currentTransform = getTransform();

        AffineTransform moveTransform = new AffineTransform();
        moveTransform.translate(dx, dy);

        moveTransform.concatenate(currentTransform);
        setTransform(moveTransform);
    }

    public void rotate(double angleDegrees) {

        double centerX = getCenterX();
        double centerY = getCenterY();

        AffineTransform currentTransform = getTransform();

        AffineTransform rotateTransform = new AffineTransform();
        rotateTransform.translate(centerX, centerY);
        rotateTransform.rotate(Math.toRadians(angleDegrees));
        rotateTransform.translate(-centerX, -centerY);

        rotateTransform.concatenate(currentTransform);
        setTransform(rotateTransform);
    }

    public void scale(double sx, double sy, double centerX, double centerY){
        AffineTransform currentTransform = getTransform();

        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.translate(centerX, centerY);
        scaleTransform.scale(sx, sy);
        scaleTransform.translate(-centerX, -centerY);

        scaleTransform.concatenate(currentTransform);
        setTransform(scaleTransform);
    }

    public double getCenterX() {
        Rectangle2D bounds = transform.createTransformedShape(originalBounds).getBounds2D();
        return bounds.getCenterX();
    }

    public double getCenterY() {
        Rectangle2D bounds = transform.createTransformedShape(originalBounds).getBounds2D();
        return bounds.getCenterY();
    }

    public Point2D[] getCorners() {

        // four corners of the original shape
        Point2D[] originalCorners = {
                new Point2D.Double(originalBounds.getMinX(), originalBounds.getMinY()), // Top-left
                new Point2D.Double(originalBounds.getMaxX(), originalBounds.getMinY()), // Top-right
                new Point2D.Double(originalBounds.getMaxX(), originalBounds.getMaxY()), // Bottom-right
                new Point2D.Double(originalBounds.getMinX(), originalBounds.getMaxY())  // Bottom-left
        };

        // corners after transformation
        Point2D[] transformedCorners = new Point2D[4];
        for (int i = 0; i < 4; i++) {
            transformedCorners[i] = transform.transform(originalCorners[i], null);
        }

        return transformedCorners;
    }


    public int getClosestCorner(Point2D point, double tolerance) {
        Point2D[] corners = getCorners();
        for (int i = 0; i < corners.length; i++) {
            if (corners[i].distance(point) <= tolerance) {
                return i;
            }
        }
        return -1;
    }

    public AffineTransform getTransform() {
        return new AffineTransform(transform);
    }

    public void setTransform(AffineTransform newTransform) {
        transform = new AffineTransform(newTransform);
    }

}