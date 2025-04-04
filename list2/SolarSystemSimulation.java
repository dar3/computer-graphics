package list2;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Planet {
    String name;
    double distance_from_sun; // distance from sun in AU
    double radius; // radius as a fraction of the Sun's radius
    Color color;
    double angle; // orbital angle
    double orbital_period;
    double moon_angle;

    public Planet(String name, double distance_from_sun, double planet_radius, Color color) {
        this.name = name;
        this.distance_from_sun = distance_from_sun;
        this.radius = planet_radius;
        this.color = color;
        angle = Math.random() * 2 * Math.PI;    // initial planet start position - here random
        moon_angle = Math.random() * 2 * Math.PI;
        orbital_period = Math.sqrt(Math.pow(distance_from_sun, 3)); // kepler's 3rd law
    }

    // simulate the motion
    public void updatePosition() {

        /*
        if (this.name.equals("Mars")) {
            this.angle += 0.01 / orbital_period;// change round direction for Mars
        } else {
            this.angle -= 0.01 / orbital_period; // rest of the planets round normally
        }
*/
        this.angle -= 0.01 / orbital_period;    // formula for speed for each planet
        if(this.name.equals("Earth"))
            this.moon_angle -= 0.13;            //moon moves 13 times faster around the earth than earth around th sun
    }
}

public class SolarSystemSimulation {
    public static void main(String[] args) {

        SolarWindow wnd = new SolarWindow();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setBounds(100, 0, 900, 900);
        wnd.setVisible(true);

        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Program interrupted");
            }
            wnd.repaint();
        }
    }
}

class SolarPane extends JPanel {

    int center_x, center_y;                         // center of the sun - center of the solar system
    int r_sun;                          // sun radius
    int AU;                            // distance from the sun to the Earth
    static double moon_r_ratio = 0.27;              //moon radius compared to earth
    static double moon_distance_ratio = 0.18;       //distance from earth to the moon in AU

    ArrayList<Planet> planets = new ArrayList<>();  //planets data

    SolarPane() {

        setBackground(new Color(0, 0, 30));

        // create planets
        planets.add(new Planet("Mercury", 0.39, 0.2, Color.GRAY));
        planets.add(new Planet("Venus", 0.72, 0.4, new Color(220, 220,100)));
        planets.add(new Planet("Earth", 1.0, 0.5, Color.GREEN));
        planets.add(new Planet("Mars", 1.27, 0.35, Color.RED));
        planets.add(new Planet("Jupiter", 1.75, 1.0, Color.ORANGE));
        planets.add(new Planet("Saturn", 2.18, 0.8, new Color(220, 220,150)));
        planets.add(new Planet("Uranus", 2.5, 0.65, Color.CYAN));
        planets.add(new Planet("Neptune", 2.75, 0.55, new Color(90,100,255)));

    }

    public void DrawPlanetTrail(Graphics g, int distance_from_sun)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.gray);
        g2d.drawOval(center_x - distance_from_sun, center_y - distance_from_sun, 2 * distance_from_sun, 2 * distance_from_sun);


    }


    public void DrawPlanetImage(Graphics g, Planet planet)
    {
        // draw orbit
        DrawPlanetTrail(g, (int) (planet.distance_from_sun * AU));

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(planet.color);

        // calculate the distance and radius in pixels
        int radius  = (int) (planet.radius * r_sun);
        int distance_from_sun = (int)(planet.distance_from_sun * AU);

        // claculate the planet's position
        int planet_x = center_x + (int) ((distance_from_sun* Math.cos(planet.angle)) - radius);
        int planet_y = center_y + (int) ((distance_from_sun * Math.sin(planet.angle)) - radius);

        // Draws a filled ellipse in a rectangle with the given dimensions. Here it's circle
        g2d.fillOval(planet_x, planet_y, 2 * radius, 2 *radius);

        if(planet.name.equals("Earth"))
            DrawMoonImage(g, planet, planet_x, planet_y);
    }

    public void DrawMoonImage(Graphics g, Planet earth , int earth_x, int earth_y)
    {
        DrawMoonTrail(g, earth, earth_x, earth_y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);

        //scaled distances
        double moon_distance = moon_distance_ratio * AU;                    // scaled distance from Earth in pixels
        int moon_radius = (int) (moon_r_ratio * earth.radius * r_sun);      // scaled Moon size
        int earth_radius = (int)(earth.radius * r_sun);

        //calculate the current earth center
        int earth_center_x = earth_x + earth_radius;
        int earth_center_y = earth_y + earth_radius;

        // calculate Moon's position relative to Earth's center
        // earth_center_x coordinates of Earths center
        int moon_x = earth_center_x + (int) ( moon_distance * Math.cos(earth.moon_angle) - moon_radius);
        int moon_y = earth_center_y + (int) ( moon_distance * Math.sin(earth.moon_angle) - moon_radius);

        g2d.fillOval(moon_x, moon_y, 2 * moon_radius, 2 * moon_radius);
    }

    public void DrawMoonTrail(Graphics g, Planet earth, int earth_x, int earth_y)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);

        // scaled distances
        int moon_distance = (int)(moon_distance_ratio * AU);
        int earth_radius = (int)(earth.radius * r_sun);

        //calculate the current earth center
        int earth_center_x = earth_x + earth_radius;
        int earth_center_y = earth_y + earth_radius;

        g2d.drawOval(earth_center_x - moon_distance, earth_center_y - moon_distance, 2 * moon_distance, 2 * moon_distance);
    }


    public void DrawSun(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        g.fillOval(center_x - r_sun, center_y - r_sun, 2*r_sun, 2*r_sun);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Dimension size = getSize();
        center_x = size.width / 2;
        center_y = (size.height) / 2;
        r_sun = Math.min(size.height, size.width)/22;
        AU = Math.min(size.height, size.width)/ 6;

        // draw sun
        DrawSun(g);

        //draw planets
        for(Planet planet : planets) {
            DrawPlanetImage(g, planet);
            planet.updatePosition();
        }
    }
}

class SolarWindow extends JFrame {
    public SolarWindow() {
        setContentPane(new SolarPane());
        setTitle("Solar System simulation");
    }
}