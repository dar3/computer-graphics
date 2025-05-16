package list6;


import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <scene file> \nScene file struct: ");
            System.out.println("1\tNumber of lights \n2\tLight sources (position x, y, z and color ER, EG, EB) each light in seperate line\n3\tSphere material (kd.rgb, ks.rgb, ka.rgb, selfLuminance.rgb, g)\n4\tSphere center x, y, z and radius\n5\tAmbient light (AR, AG, AB)\n6\tResolution width height\n7\tOutput image filename");
            return;
        }

        String sceneFile = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(sceneFile))) {
            int numLights = Integer.parseInt(br.readLine().trim());
            Light[] lights = new Light[numLights];

//            parsowanie liczby i parametrow swiatel
            for (int i = 0; i < numLights; i++) {
                String[] tokens = br.readLine().trim().split("\\s+");
                Vector3D position = new Vector3D(
                        Double.parseDouble(tokens[0]),
                        Double.parseDouble(tokens[1]),
                        Double.parseDouble(tokens[2])
                );
                Vector3D intensity = new Vector3D(
                        Double.parseDouble(tokens[3]),
                        Double.parseDouble(tokens[4]),
                        Double.parseDouble(tokens[5])
                );
                lights[i] = new Light(position, intensity);
            }

//            parametry i material kuli
            String[] materialTokens = br.readLine().trim().split("\\s+");
            Vector3D kd = new Vector3D(
                    Double.parseDouble(materialTokens[0]),
                    Double.parseDouble(materialTokens[1]),
                    Double.parseDouble(materialTokens[2])
            );
            Vector3D ks = new Vector3D(
                    Double.parseDouble(materialTokens[3]),
                    Double.parseDouble(materialTokens[4]),
                    Double.parseDouble(materialTokens[5])
            );
            Vector3D ka = new Vector3D(
                    Double.parseDouble(materialTokens[6]),
                    Double.parseDouble(materialTokens[7]),
                    Double.parseDouble(materialTokens[8])
            );
            Vector3D sl = new Vector3D(
                    Double.parseDouble(materialTokens[9]),
                    Double.parseDouble(materialTokens[10]),
                    Double.parseDouble(materialTokens[11])
            );
            double g = Double.parseDouble(materialTokens[12]);

            Material material = new Material(kd, ks, ka, sl, g);

//            pozycja i rozmiar kuli
            String[] sphereTokens = br.readLine().trim().split("\\s+");
            Vector3D center = new Vector3D(
                    Double.parseDouble(sphereTokens[0]),
                    Double.parseDouble(sphereTokens[1]),
                    Double.parseDouble(sphereTokens[2])
            );
            double radius = Double.parseDouble(sphereTokens[3]);

            Sphere sphere = new Sphere(radius, center, material);

//            swiatlo otoczenia
            String[] ambientTokens = br.readLine().trim().split("\\s+");
            Vector3D ambientLight = new Vector3D(
                    Double.parseDouble(ambientTokens[0]),
                    Double.parseDouble(ambientTokens[1]),
                    Double.parseDouble(ambientTokens[2])
            );

//            rozdzielczosc obrazu
            String[] resolutionTokens = br.readLine().trim().split("\\s+");
            int width = Integer.parseInt(resolutionTokens[0]);
            int height = Integer.parseInt(resolutionTokens[1]);

            String outputFilename = br.readLine().trim();

            Scene scene = new Scene(lights, sphere, ambientLight, height, width);

            // render
            Vector3D attenuation = new Vector3D(0.5, 0.0, 0.0);
            PhongShadingModel shader = new PhongShadingModel(attenuation);
            BufferedImage output = shader.shade(scene);

            // save image
            File outputfile = new File(outputFilename);
            ImageIO.write(output, "png", outputfile);
            System.out.println("Image saved as " + outputFilename);

            // display
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new JLabel(new ImageIcon(output)));
            frame.pack();
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}