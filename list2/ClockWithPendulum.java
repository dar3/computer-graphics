package list2;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;


public class ClockWithPendulum {

    public static void main(String[] args) {
        int pendulum_angle = Integer.parseInt(args[0].trim());
        int pendulum_period =Integer.parseInt(args[1].trim());

        ClockWindow wnd = new ClockWindow(pendulum_angle, pendulum_period);
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setBounds(70, 70, 310, 310);
        wnd.setVisible(true);

        while (true) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                System.out.println("Program interrupted");
            }
            wnd.repaint();
        }
    }
}

class ClockPane extends JPanel {
    final int TICK_LEN = 10;
    int center_x_axis, center_y_axis;
    int r_outer, r_inner;
    GregorianCalendar calendar;
    int pendulum_angle, pendulum_period, pendulum_length;
    int ball_radius;
    int start_time;

    ClockPane(int pendulum_angle, int pendulum_period) {
        this.pendulum_angle = pendulum_angle;
        this.pendulum_period = pendulum_period;
        start_time = (int)System.currentTimeMillis();
        setBackground(new Color(121, 121, 128));
        calendar = new GregorianCalendar();
    }

    public void DrawTickMark(double angle, Graphics g) {
        int xw, yw, xz, yz;
        angle = 3.1415 * angle / 180.0;
        xw = (int) (center_x_axis + r_inner * Math.sin(angle));
        yw = (int) (center_y_axis - r_inner * Math.cos(angle));
        xz = (int) (center_x_axis + r_outer * Math.sin(angle));
        yz = (int) (center_y_axis - r_outer * Math.cos(angle));
        g.drawLine(xw, yw, xz, yz);
    }

    public void DrawHand(double angle, int length, Graphics g) {
        int xw, yw, xz, yz;
        angle = 3.1415 * angle / 180.0;
        xw = (int) (center_x_axis + length * Math.sin(angle));
        yw = (int) (center_y_axis - length * Math.cos(angle));
        angle += 3.1415;
        xz = (int) (center_x_axis + TICK_LEN * Math.sin(angle));
        yz = (int) (center_y_axis - TICK_LEN * Math.cos(angle));
        g.drawLine(xw, yw, xz, yz);
    }

    public void DrawDial(Graphics g) {
        g.drawOval(center_x_axis - r_outer, center_y_axis - r_outer, 2 * r_outer, 2 * r_outer);
        for (int i = 0; i <= 11; i++) {
            DrawTickMark(i * 30.0, g);
        }
    }


    public void DrawPendulumImage(Graphics g, int start_x, int start_y) {

        // start_x start_y point of pendulum attachment
        int x, y;
        int angle = getPendulumAngle();
        // sin for horizontal movement
        // cos for vertical movement
        // converting angles to radians
        x = start_x + (int) (pendulum_length * Math.sin(Math.PI * angle / 180.0));
        y = start_y  + (int) (pendulum_length * Math.cos(Math.PI * angle / 180.0));
        g.drawLine(start_x , start_y, x, y);

//        int ball_x = (start_x + x) / 2;
//        int ball_y = (start_y + y) / 2;

        int ball_x = x - (int) (ball_radius * Math.sin(Math.PI * angle / 180.0));
        int ball_y = y - (int) (ball_radius * Math.cos(Math.PI * angle / 180.0));
        g.fillOval(ball_x - ball_radius, ball_y - ball_radius, ball_radius * 2, ball_radius * 2);
    }




    public int getPendulumAngle() {
        double timeElapsed = System.currentTimeMillis() - start_time;
        // 2 * Math.PI / pendulum_period  calculates the circular frequency of the pendulum motion:
        return (int)(pendulum_angle * Math.cos((2 * Math.PI / pendulum_period) * timeElapsed));
    }

    public void paintComponent(Graphics g) {
        int minute, second, hour;
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Dimension size = getSize();



        Date time = new Date();
        calendar.setTime(time);
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR);
        if (hour > 11) hour = hour - 12;
        second = calendar.get(Calendar.SECOND);

        DrawDial(g);
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(5));
        DrawHand(360.0 * (hour * 60 + minute) / (60.0 * 12), (int) (0.75 * r_inner), g);
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(3));
        DrawHand(360.0 * (minute * 60 + second) / 3600.0, (int) (0.97 * r_outer), g);
        g2d.setColor(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(1));
        DrawHand(second * 6.0, (int) (0.97 * r_inner), g);

        DrawPendulumImage(g2d, center_x_axis, center_y_axis - r_outer);

        // setting parameters
        ball_radius = size.height / 20;// radius of the ball is always 1/20 of the pane size to be responsive
        // center of dial horizontally
        center_x_axis = size.width / 2;
        // center of dial vertically
        center_y_axis = (size.height - ball_radius) / 6; // center of the clock's dial
        // Min so the pendulum is not too long
        r_outer = Math.min(size.width/2, (size.height - ball_radius)/6) ;    // setting the max dial radius
        r_inner = r_outer - TICK_LEN;
        // 4 * center pendulum length, based on the position of the clock face.
        pendulum_length = Math.min(4* center_y_axis, (int)(center_x_axis /Math.sin(Math.PI * pendulum_angle / 180.0)));
    }
}

class ClockWindow extends JFrame {
    public ClockWindow(int pendulum_angle, int pendulum_period) {
        setContentPane(new ClockPane(pendulum_angle, pendulum_period));
        setTitle("Clock with pendulum");
    }
}

// run in CMD
// java .\ClockWithPendulum.java 30 2000