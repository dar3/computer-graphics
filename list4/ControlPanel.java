package list4;

import java.awt.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
    JButton button, saveVector, loadVector, saveRaster, moveLeft, moveRight, moveUp, moveDown, rotLeft, rotRight;
    JTextField redField, greenField, blueField;
    JRadioButton rotateButton, resizeButton;
    ButtonGroup reGroup;
    DrawWndPane drawPane;

    public ControlPanel(DrawWndPane drawPane) {
        this.drawPane = drawPane;

        button = new JButton("Reset");
        saveVector = new JButton("Save txt");
        loadVector = new JButton("Load");
        saveRaster = new JButton("Save img");

        moveLeft = new JButton("Left");
        moveRight = new JButton("Right");
        moveUp = new JButton("Up");
        moveDown = new JButton("Down");

        rotateButton = new JRadioButton("Rotate", true);
        resizeButton = new JRadioButton("Resize");
        reGroup = new ButtonGroup();
        reGroup.add(rotateButton);
        reGroup.add(resizeButton);

        rotLeft = new JButton("Anticlockwise");
        rotRight = new JButton("Clockwise");

        redField = new JTextField("0", 3);
        greenField = new JTextField("0", 3);
        blueField = new JTextField("0", 3);

        redField.setBackground(Color.red);
        greenField.setBackground(Color.green);
        blueField.setBackground(new Color(100,100, 255));

        setLayout(new GridLayout(2, 1)); // two rows

        // first row panel
        JPanel row1 = new JPanel(new FlowLayout());
        row1.add(button);
        row1.add(saveVector);
        row1.add(loadVector);
        row1.add(saveRaster);
        row1.add(new JLabel("Move:"));
        row1.add(moveLeft);
        row1.add(moveUp);
        row1.add(moveRight);
        row1.add(moveDown);

        // second row panel
        JPanel row2 = new JPanel(new FlowLayout());
        row2.add(rotateButton);
        row2.add(resizeButton);
        row2.add(new JLabel("Rotate:"));
        row2.add(rotLeft);
        row2.add(rotRight);
        row2.add(new JLabel("Color:"));
        row2.add(redField);
        row2.add(greenField);
        row2.add(blueField);

        add(row1);
        add(row2);

        // adding actions
        button.addActionListener(e -> drawPane.removeAction(e));
        saveVector.addActionListener(e -> drawPane.saveToTextFile());
        loadVector.addActionListener(e -> drawPane.loadFromTxt());
        saveRaster.addActionListener(e -> drawPane.saveAsImage());

        moveLeft.addActionListener(e -> drawPane.moveItem("Left"));
        moveUp.addActionListener(e -> drawPane.moveItem("Up"));
        moveRight.addActionListener(e -> drawPane.moveItem("Right"));
        moveDown.addActionListener(e -> drawPane.moveItem("Down"));

        rotLeft.addActionListener(e -> drawPane.rotItem(false));
        rotRight.addActionListener(e -> drawPane.rotItem(true));

        rotateButton.addActionListener(e -> drawPane.setRotateMode(true));
        resizeButton.addActionListener(e -> drawPane.setRotateMode(false));
    }
}