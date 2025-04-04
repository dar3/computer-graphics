package list3;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DrawPane extends JPanel implements MouseListener, MouseMotionListener {
    private String currentShape = "Line";
    private ArrayList<Shape> shapes = new ArrayList<>();
    private ArrayList<Color> shapeColors = new ArrayList<>();
    private int shapeCaught = -1, pointCaught;
    private ControlPanel controlPanel;

    public DrawPane() {
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    // painting shapes. Panel needs to be refreshed
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < shapes.size(); i++) {
            g2d.setColor(shapeColors.get(i));
            if (shapes.get(i) instanceof Line2D) {

                g2d.draw(shapes.get(i));
            } else {

                g2d.fill(shapes.get(i));
            }
        }
    }

    private void bringShapeToFront(int shapeIndex){
        if(shapeIndex >= 0 && shapeIndex < shapes.size()){
            Shape shape = shapes.remove(shapeIndex);
            Color color = shapeColors.remove(shapeIndex);
            shapes.add(shape);
            shapeColors.add(color);
            repaint();
        }
    }

    // Reszta metod pozostaje bez zmian
    public void setCurrentShape(String shape) {
        this.currentShape = shape;
    }

    public void loadVectorFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a txt file to load");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            shapes.clear();
            shapeColors.clear();

            try (Scanner scanner = new Scanner(selectedFile, StandardCharsets.UTF_8)) {
                while (scanner.hasNext()) {
                    String type = scanner.next();
                    double x = Double.parseDouble(scanner.next());
                    double y = Double.parseDouble(scanner.next());
                    double w = Double.parseDouble(scanner.next());
                    double h = Double.parseDouble(scanner.next());
                    int r = scanner.nextInt();
                    int g = scanner.nextInt();
                    int b = scanner.nextInt();
                    Color color = new Color(r, g, b);

                    switch (type) {
                        case "Line" -> shapes.add(new Line2D.Double(x, y, w, h));
                        case "Rectangle" -> shapes.add(new Rectangle2D.Double(x, y, w, h));
                        case "Circle" -> shapes.add(new Ellipse2D.Double(x, y, w, h));
                    }
                    shapeColors.add(color);
                }
                repaint();
                JOptionPane.showMessageDialog(null, "Vector image loaded successfully!");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void saveVectorToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Vector Image");

        fileChooser.setSelectedFile(new File("vector_image.txt"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }

            try (PrintWriter writer = new PrintWriter(selectedFile)) {
                for (int i = 0; i < shapes.size(); i++) {
                    Shape shape = shapes.get(i);
                    Color color = shapeColors.get(i);

                    if (shape instanceof Line2D.Double line) {
                        writer.printf("Line %.2f %.2f %.2f %.2f %d %d %d%n",
                                line.x1, line.y1, line.x2, line.y2,
                                color.getRed(), color.getGreen(), color.getBlue());
                    } else if (shape instanceof Rectangle2D.Double rect) {
                        writer.printf("Rectangle %.2f %.2f %.2f %.2f %d %d %d%n",
                                rect.x, rect.y, rect.width, rect.height,
                                color.getRed(), color.getGreen(), color.getBlue());
                    } else if (shape instanceof Ellipse2D.Double circ) {
                        writer.printf("Circle %.2f %.2f %.2f %.2f %d %d %d%n",
                                circ.x, circ.y, circ.width, circ.height,
                                color.getRed(), color.getGreen(), color.getBlue());
                    }
                }
                JOptionPane.showMessageDialog(null, "Vector image saved as: " + selectedFile.getName());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void saveAsImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image As");

        fileChooser.setSelectedFile(new File("image.png"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
            }

            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            paint(g2d);
            g2d.dispose();

            try {
                ImageIO.write(image, "PNG", selectedFile);
                JOptionPane.showMessageDialog(null, "Image saved as: " + selectedFile.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error saving image!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean CatchClosePoint(double x, double y) {
        for (int i = 0; i < shapes.size(); i++) {
            Point2D center = getShapeCenter(shapes.get(i));

            if (shapes.get(i) instanceof Line2D.Double) {
                Line2D.Double line = (Line2D.Double) shapes.get(i);

                if ((Math.abs(line.x1 - x) < 10) && (Math.abs(line.y1 - y) < 10)) {
                    shapeCaught = i;
                    pointCaught = 0;
                    return true;
                }
                if ((Math.abs(line.x2 - x) < 10) && (Math.abs(line.y2 - y) < 10)) {
                    shapeCaught = i;
                    pointCaught = 1;
                    return true;
                }
                double xc = 0.5 * (line.x1 + line.x2);
                double yc = 0.5 * (line.y1 + line.y2);
                if ((Math.abs(xc - x) < 10) && (Math.abs(yc - y) < 10)) {
                    shapeCaught = i;
                    pointCaught = 2;
                    return true;
                }
            } else if (shapes.get(i) instanceof Rectangle2D.Double) {
                Rectangle2D.Double rect = (Rectangle2D.Double) shapes.get(i);

                if ((Math.abs(rect.x - x) < 10) && (Math.abs(rect.y - y) < 10)) {
                    shapeCaught = i;
                    pointCaught = 0;
                    return true;
                }
                if ((Math.abs(rect.x + rect.width - x) < 10) && (Math.abs(rect.y + rect.height - y) < 10)) {
                    shapeCaught = i;
                    pointCaught = 1;
                    return true;
                }
                if (center.distance(x, y) < 10) {
                    shapeCaught = i;
                    pointCaught = 2;
                    return true;
                }
            } else if (shapes.get(i) instanceof Ellipse2D.Double) {
                Ellipse2D.Double circ = (Ellipse2D.Double) shapes.get(i);

                if (center.distance(x, y) < 10) {
                    shapeCaught = i;
                    pointCaught = 0;
                    return true;
                }
                if (Math.abs(center.distance(x, y) - circ.height / 2) < 10) {
                    shapeCaught = i;
                    pointCaught = 1;
                    return true;
                }
            }
        }
        return false;
    }

    private void updateShapePosition(int x, int y) {
        if (shapeCaught >= 0) {
            Graphics g = getGraphics();
            Graphics2D g2d = (Graphics2D) g;

            g2d.setXORMode(Color.white);
            g2d.setColor(shapeColors.get(shapeCaught));

            Shape shape = shapes.get(shapeCaught);
            if (shape instanceof Line2D) {
                g2d.draw(shape);
            } else {
                g2d.fill(shape);
            }

            if (shape instanceof Line2D.Double line) {
                updateLine(line, x, y);
            } else if (shape instanceof Rectangle2D.Double rect) {
                updateRectangle(rect, x, y);
            } else if (shape instanceof Ellipse2D.Double circ) {
                updateCircle(circ, x, y);
            }

            g2d.setColor(getCurrentColor());
            shapeColors.set(shapeCaught, getCurrentColor());
            if (shape instanceof Line2D) {
                g2d.draw(shape);
            } else {
                g2d.fill(shape);
            }
            g2d.setPaintMode();
        }
    }

    private void updateLine(Line2D.Double line, int x, int y) {
        switch (pointCaught) {
            case 0 -> {
                line.x1 = x;
                line.y1 = y;
            }
            case 1 -> {
                line.x2 = x;
                line.y2 = y;
            }
            case 2 -> {
                double xc = x - (line.x1 + line.x2) / 2;
                double yc = y - (line.y1 + line.y2) / 2;
                line.x1 += xc;
                line.y1 += yc;
                line.x2 += xc;
                line.y2 += yc;
            }
        }
    }

    private void updateRectangle(Rectangle2D.Double rect, int x, int y) {
        switch (pointCaught) {
            case 0 -> {
                rect.width += (rect.x - x);
                rect.height += (rect.y - y);
                rect.x = x;
                rect.y = y;
            }
            case 1 -> {
                rect.x = Math.min(rect.x, x);
                rect.y = Math.min(rect.y, y);
                rect.width = Math.abs(rect.x - x);
                rect.height = Math.abs(rect.y - y);
            }
            case 2 -> {
                Point2D center = getShapeCenter(rect);
                rect.x += (x - center.getX());
                rect.y += (y - center.getY());
            }
        }
    }

    private void updateCircle(Ellipse2D.Double circ, int x, int y) {
        Point2D center = getShapeCenter(circ);
        switch (pointCaught) {
            case 0 -> {
                circ.x += (x - center.getX());
                circ.y += (y - center.getY());
            }
            case 1 -> {
                double radius = center.distance(x, y);
                circ.width = circ.height = 2 * radius;
                circ.x = center.getX() - radius;
                circ.y = center.getY() - radius;
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

            return new Color(r, g, b);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid RGB values (0-255)", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Color.BLACK;
    }

    private Point2D getShapeCenter(Shape shape) {
        if (shape instanceof Line2D) {
            Line2D line = (Line2D) shape;
            return new Point2D.Double((line.getX1() + line.getX2()) / 2, (line.getY1() + line.getY2()) / 2);
        } else if (shape instanceof Rectangle2D) {
            Rectangle2D rect = (Rectangle2D) shape;
            return new Point2D.Double(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
        } else if (shape instanceof Ellipse2D) {
            Ellipse2D circle = (Ellipse2D) shape;
            return new Point2D.Double(circle.getX() + circle.getWidth() / 2, circle.getY() + circle.getHeight() / 2);
        }
        return new Point2D.Double(0, 0);
    }

    public void removeShape() {
        Graphics g = getGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setXORMode(new Color(255, 255, 255));
        g2d.setColor(shapeColors.get(shapeCaught));
        Shape shape = shapes.get(shapeCaught);
        if (shape instanceof Line2D) {
            g2d.draw(shape);
        } else {
            g2d.fill(shape);
        }
        shapeCaught = -1;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public void actionPerformed(ActionEvent event) {
        shapes.clear();
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        // Sprawdzamy czy kliknięto prawym przyciskiem (usuwanie)
        if (SwingUtilities.isRightMouseButton(e)) {
            for (int i = 0; i < shapes.size(); i++) {
                Shape shape = shapes.get(i);
                Point2D center = getShapeCenter(shape);

                if (center.distance(e.getX(), e.getY()) < 10) {
                    shapeCaught = i;
                    removeShape();
                    shapes.remove(i);
                    shapeColors.remove(i);
                    return;
                }
            }
        }
        // Kliknięcie lewym przyciskiem
        else {

            boolean shapeClicked = false;


            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (isPointInShape(e.getX(), e.getY(), shapes.get(i))) {
                    bringShapeToFront(i);
                    shapeCaught = shapes.size() - 1; // Teraz jest ostatni na liście

                    if (CatchClosePoint(e.getX(), e.getY())) {
                        updateShapePosition(e.getX(), e.getY());
                    }
                    shapeClicked = true;
                    break;
                }
            }

            // Jeśli nie kliknięto na istniejący kształt, tworzymy nowy
            if (!shapeClicked) {
                switch (currentShape) {
                    case "Line" -> shapes.add(new Line2D.Double(e.getX(), e.getY(), e.getX(), e.getY()));
                    case "Rectangle" -> shapes.add(new Rectangle2D.Double(e.getX(), e.getY(), 0, 0));
                    case "Circle" -> shapes.add(new Ellipse2D.Double(e.getX(), e.getY(), 0, 0));
                    default -> {
                    }
                }
                shapeCaught = shapes.size() - 1;
                shapeColors.add(getCurrentColor());
                pointCaught = 1;
            }
        }
    }

    private boolean isPointInShape(int x, int y, Shape shape) {
        if (shape instanceof Line2D) {
            Line2D line = (Line2D) shape;
            return line.ptLineDist(x, y) < 5; // Margines 5 pikseli dla linii
        } else {
            return shape.contains(x, y);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (shapeCaught >= 0) {
            updateShapePosition(e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
        shapeCaught = -1;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}