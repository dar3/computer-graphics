package list4;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {
    private ArrayList<BufferedImage> images = new ArrayList<>();
    private ArrayList<Integer> imageY = new ArrayList<>();
    private BufferedImage draggedImage = null;
    private String draggedPath;
    private DrawWndPane drawPane;
    private DragGlassPane glassPane;
    private String DIR_PATH = "D:\\Documents\\Programming_projects\\ComputerGraphics\\images";
    int IMAGE_SIZE = 100;
    int IMAGE_X = 55;
    File[] files;
    JLabel label;
    JButton changeDirButton;


    public ImagePanel() {
        setPreferredSize(new Dimension(200, 400));
        setBackground(Color.blue);

        label = new JLabel("Assets: ");
        label.setPreferredSize(new Dimension(150, 20));

        changeDirButton = new JButton("Change DIR");
        changeDirButton.setPreferredSize(new Dimension(150,20));

        add(changeDirButton);
        add(label);

        loadImagesFromDirectory(DIR_PATH);

        addMouseListener(this);
        addMouseMotionListener(this);
        changeDirButton.addActionListener(e -> {
            JFileChooser dirChooser = new JFileChooser();
            dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            dirChooser.setDialogTitle("Select Image Directory");

            int result = dirChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedDir = dirChooser.getSelectedFile();
                if (selectedDir != null && selectedDir.isDirectory()) {
                    DIR_PATH = selectedDir.getAbsolutePath();
                    loadImagesFromDirectory(DIR_PATH);
                    repaint();
                }
            }
        });

    }

    private void loadImagesFromDirectory(String path) {
        images.clear();
        File folder = new File(path);
        files = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg")
        );

        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage img = ImageIO.read(file);
                    if (img != null) {
                        images.add(img);
                    }
                } catch (Exception e) {
                    System.out.println("Could not load: " + file.getName());
                }
            }
        }
    }


    public void setDrawPane(DrawWndPane drawWndPane){
        this.drawPane = drawWndPane;
    }
    public void setGlassPane(DragGlassPane glassPane){
        this.glassPane = glassPane;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        imageY.clear();
        int x = IMAGE_X, y = 50;
        int spacing = 10;

        for (BufferedImage img : images) {
            if (img != null) {
                g.drawImage(img, x, y, IMAGE_SIZE, IMAGE_SIZE, this);
                imageY.add(y);
                y += IMAGE_SIZE + spacing;
            }
        }

    }


    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < imageY.size(); i++) {
            int x = IMAGE_X;
            int y = imageY.get(i);

            if (e.getX() > x && e.getX() < x + IMAGE_SIZE && e.getY() > y && e.getY() < y + IMAGE_SIZE) {
                draggedImage = images.get(i);
                draggedPath = files[i].getAbsolutePath();

                Point screenPoint = SwingUtilities.convertPoint(this, e.getPoint(), getRootPane());
                glassPane.setImage(draggedImage, screenPoint);
                break;
            }
        }
    }



    public void mouseDragged(MouseEvent e) {
        if (draggedImage != null && drawPane != null) {
            Point screenPoint = SwingUtilities.convertPoint(this, e.getPoint(), getRootPane());
            glassPane.updateLocation(screenPoint);
        }
    }



    public void mouseReleased(MouseEvent e) {
        if (draggedImage != null && drawPane != null) {
            Point dropPoint = SwingUtilities.convertPoint(this, e.getPoint(), drawPane);
            drawPane.dropImage(draggedImage, dropPoint.x - IMAGE_SIZE/2, dropPoint.y - IMAGE_SIZE/2, IMAGE_SIZE, IMAGE_SIZE, draggedPath);
        }

        // clear the temp view
        ((DragGlassPane) getRootPane().getGlassPane()).clearImage();
        draggedImage = null;
        draggedPath = "";
    }


    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}