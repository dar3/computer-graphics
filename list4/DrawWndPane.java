package list4;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawWndPane extends JPanel implements MouseListener, MouseMotionListener {
    private int itemCaught = -1, pointCaught, lastMouseX,lastMouseY;
    private ArrayList<DrawableItem> items = new ArrayList<>();
    boolean isRotateMode = true;

    private ControlPanel controlPanel;
    private Graphics2D g2d;

    public void dropImage(BufferedImage img, int x, int y, int width, int height, String imagePath) {
        items.add(new ImageItem(img, x, y, width, height, imagePath));
        itemCaught = items.size()-1;
        repaint();
    }
    public void dropShape(Shape shape, boolean isCirc) {
        items.add(new ShapeItem(shape, getCurrentColor(), isCirc));
        itemCaught = items.size()-1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        for (DrawableItem d : items) {
            d.draw(g2d);
        }
        if(itemCaught >= 0 && items.get(itemCaught) instanceof ShapeItem){
            ShapeItem item = (ShapeItem) items.get(itemCaught);
            if(item.isCirc){
                item.drawBounds(g2d);
            }


        }
    }

    public DrawWndPane() {
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    public void setControlPanel(ControlPanel controlPanel){
        this.controlPanel = controlPanel;
    }

    void setRotateMode(boolean rotate){
        isRotateMode = rotate;
    }


    public void loadFromTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load poster from text");

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String baseDir = selectedFile.getParent();

            items.clear(); // Clear existing items

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("RECT,") || line.startsWith("CIRC,")) {
                        ShapeItem item = ManagerItems.deserialize(line);
                        if (item != null) items.add(item);
                    } else if (line.startsWith("IMAGE,")) {
                        ImageItem item = ManagerItems.deserializeImage(line, baseDir);
                        if (item != null) items.add(item);
                    }
                }
                repaint();
                JOptionPane.showMessageDialog(null, "Poster loaded successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error loading file!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    public void saveToTextFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As Text");
        fileChooser.setSelectedFile(new File("poster.txt"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }

            try (PrintWriter writer = new PrintWriter(selectedFile)) {
                // saving items
                for (DrawableItem item : items) {
                    writer.println(item.serialize());
                }
                JOptionPane.showMessageDialog(null, "Poster saved to: " + selectedFile.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void saveAsImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image As");
        fileChooser.setSelectedFile(new File("image.png"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
            }

            try {
                // calculate total bounds
                Rectangle2D totalBounds = null;
                for (DrawableItem item : items) {
                    Shape transformed = item.transform.createTransformedShape(item.getOriginalShape());
                    Rectangle2D bounds = transformed.getBounds2D();
                    if (totalBounds == null) {
                        totalBounds = bounds;
                    } else {
                        totalBounds = totalBounds.createUnion(bounds);
                    }
                }

                int padding = 20;
                int imageWidth = (int) Math.ceil(totalBounds.getWidth()+2*padding);
                int imageHeight = (int) Math.ceil(totalBounds.getHeight()+2*padding);

                // image with enough space
                BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D gBuffer = image.createGraphics();

                gBuffer.setColor(Color.WHITE);
                gBuffer.fillRect(0, 0, imageWidth, imageHeight);

                // adjust offset
                AffineTransform transform = new AffineTransform();
                transform.translate(
                        padding - totalBounds.getX(),
                        padding - totalBounds.getY()
                );
                gBuffer.transform(transform);

                // drawing all the items
                for (DrawableItem item : items) {
                    item.draw(gBuffer);
                }

                gBuffer.dispose();

                //saving img
                ImageIO.write(image, "PNG", selectedFile);
                JOptionPane.showMessageDialog(null, "Image saved as: " + selectedFile.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog( null, "Error saving image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    private Color getCurrentColor() {
        try {
            int r = Integer.parseInt(controlPanel.redField.getText());
            int g = Integer.parseInt(controlPanel.greenField.getText());
            int b = Integer.parseInt(controlPanel.blueField.getText());

            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

            return new Color(r,g,b);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid RGB values (0-255)", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Color.BLACK; //black as default
    }

    private boolean CatchClosePoint(int  x, int y)
    {
        for ( int i = 0; i < items.size(); i++ )
        {
            DrawableItem item = items.get(i);
            Point center = new Point((int)item.getCenterX(), (int)item.getCenterY());
            Point currentPoint = new Point(x,y);

            if(center.distance(currentPoint) < 20)
            {
                items.add(item);
                items.remove(i);
                itemCaught = items.size()-1;
                pointCaught = 0;
                return true;
            }
            if(item.getClosestCorner(currentPoint, 10) >= 0){
                items.add(item);
                items.remove(i);
                itemCaught = items.size()-1;
                pointCaught = 1;
                return true;
            }
        }
        return false;
    }

    public void moveItem(String direction){
        if(itemCaught >= 0){
            double pixels = 1;
            switch(direction){
                case "Left" -> items.get(itemCaught).translate(-pixels, 0);
                case "Right" -> items.get(itemCaught).translate(pixels, 0);
                case "Up" -> items.get(itemCaught).translate(0, -pixels);
                case "Down" -> items.get(itemCaught).translate(0, pixels);
            }
            repaint();
        }
    }

    public void rotItem(boolean clockwise){
        if (itemCaught >= 0) {
            double angle = clockwise ? 1 : -1;
            items.get(itemCaught).rotate(angle);
            repaint();
        }
    }

    public void removeAction(ActionEvent event) {
        items.clear();
        itemCaught = -1;
        pointCaught = -1;
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (CatchClosePoint(e.getX(), e.getY())){
                if(itemCaught >= 0 && pointCaught == 0)
                {
                    items.remove(itemCaught);
                    itemCaught = -1;
                    pointCaught = -1;
                    repaint();
                }
            }
        }
        else {
            if (CatchClosePoint(e.getX(), e.getY())) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        }
    }


    public void mouseDragged(MouseEvent e) {
        if (itemCaught >= 0) {
            DrawableItem item = items.get(itemCaught);
            // center - move
            if (pointCaught == 0) {
                double dx = e.getX() - lastMouseX;
                double dy = e.getY() - lastMouseY;
                item.translate(dx, dy);
            }
            // corner - rotate/resize
            else if (pointCaught == 1 ) {
                double centerX = item.getCenterX();
                double centerY = item.getCenterY();
                //rotate
                if(isRotateMode){
                    // angle change
                    double prevAngle = Math.atan2(lastMouseY - centerY, lastMouseX - centerX);
                    double currAngle = Math.atan2(e.getY() - centerY, e.getX() - centerX);
                    double angleDiff = Math.toDegrees(currAngle - prevAngle);

                    item.rotate(angleDiff);
                }
                //resize
                else{
                    // ratio of the new size to the previous
                    double sx = (e.getX()-centerX)/(double)(lastMouseX - centerX) ;
                    double sy =(e.getY()-centerY)/(double)(lastMouseY - centerY) ;

                    item.scale(sx, sy, centerX, centerY);
                }
            }
            lastMouseX = e.getX();
            lastMouseY = e.getY();

            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        pointCaught = -1;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

}