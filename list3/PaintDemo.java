package list3;


import javax.swing.*;
import java.awt.*;

public class PaintDemo {
    public static void main(String[] args) {
        SmpWindow wnd = new SmpWindow();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setVisible(true);
        wnd.setBounds(70, 70, 900, 500);
        wnd.setTitle("Vector editor");
    }
}

class SmpWindow extends JFrame {
    public SmpWindow() {
        setLayout(new BorderLayout());

        DrawPane drawPane = new DrawPane();
        ControlPanel controlPanel = new ControlPanel(drawPane);
        drawPane.setControlPanel(controlPanel);

        add(controlPanel, BorderLayout.SOUTH);
        add(drawPane, BorderLayout.CENTER);
    }
}
