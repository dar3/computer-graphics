package list6;

//Laczy wszystkie elementy sceny w calosc

public class Scene {
    public Light[] lightSources; // tablica swiatel
    public Sphere sphere; // Kula do renderowania
    public Vector3D ambientLight; // AR, AG, AB
    public int imageHeight, imageWidth;

    public Scene(Light[] lightSources, Sphere sphere, Vector3D ambientLight, int imageHeight, int imageWidth) {
        this.lightSources = lightSources;
        this.sphere = sphere;
        this.ambientLight = ambientLight;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }
}