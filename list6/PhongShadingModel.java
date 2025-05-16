package list6;

import java.awt.Color;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;

public class PhongShadingModel {

    private final double c0;
    private final double c1;
    private final double c2;

    public PhongShadingModel(Vector3D attenuation) {
        this.c0 = attenuation.x;
        this.c1 = attenuation.y;
        this.c2 = attenuation.z;
    }

    public double fAtt(double r) {
        double denominator = c2 * r * r + c1 * r + c0;
        if (denominator == 0.0) return 1.0;
        return Math.min(1.0 / denominator, 1.0);
    }

    public BufferedImage shade(Scene scene) {
        int width = scene.imageWidth;
        int height = scene.imageHeight;
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Sphere sphere = scene.sphere;
        Material material = sphere.material;
        double r = sphere.radius;
        double rPow = r * r;
        Vector3D viewDir = new Vector3D(0.0, 0.0, 1.0); // we look from z axis - kierunek obserwatora


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                obliczanie pozycji w przestrzeni 3D
                double[] pos = realPosition(r, i, height, j, width);
                double x = pos[0];
                double y = pos[1];
//                oblicz kolor piksela
                int color = getPixelColor(x, y, rPow, material, scene.ambientLight, scene.lightSources, viewDir);
//                zapisywanie koloru do obrazu
                imageOutput.setRGB(j, i, color);
            }
        }
        return imageOutput;
    }

    private double[] realPosition(double r, int i, int height, int j, int width) {
        int n = min(width,height);
        double y = r * (1 - 2.0 * i / n);
        double x = r * (2.0 * j / n - 1);
        return new double[]{x, y};
    }

    private int getPixelColor(double x, double y, double rPow, Material material, Vector3D ambientLight, Light[] lights, Vector3D viewDir) {

//        sprawdzanie czy punkt nalezy do kuli
        if (x * x + y * y > rPow) {
            return Color.BLACK.getRGB();  // outside of the sphere - black
        }

        double z = sqrt(rPow - x * x - y * y); // from x2+y2+z2=r2

        Vector3D point = new Vector3D(x, y, z);
//        obliczanie wektora normalnego
        Vector3D normal = point.normalize();
//        obliczanie koloru podstawowego ambient + self luminance (ka * Ia) ka -kolor ambientu
        Vector3D color = material.sl.add(material.ka.multiply(ambientLight)); //sc + kac*Ac

        for (Light light : lights) {
//            obliczenie kierunku swiatla
            Vector3D lightDir = light.position.substr(point).normalize();
            Vector3D reflectDir = normal.multiply(2.0 * normal.dot(lightDir)).substr(lightDir); //R = 2 (N dot I)N - I
//            rozproszenie (kd * (N·L) * I)
//            kd - kolor rozproszenia
            Vector3D diffuse = material.kd.multiply(light.intensity).multiply(Math.max(normal.dot(lightDir), 0.0)); // kdc * Eic * N * I
//            odbicie ks - kolor refleksu (odbicia)
            Vector3D specular = material.ks.multiply(light.intensity).multiply(Math.pow(Math.max(viewDir.dot(reflectDir), 0.0), material.g)); // ksc * Eic * (I * O) ^ g
            color = color.add(diffuse.add(specular).multiply(fAtt(light.position.substr(point).length())));  // added and all multiplied by fatt(r)
        }

        return vectorToColorRGB(color);
    }

    private int vectorToColorRGB(Vector3D color) {
        Vector3D clampedColor = color.clamp(255.0);
        return new Color((int) clampedColor.x, (int) clampedColor.y, (int) clampedColor.z).getRGB();
    }
}