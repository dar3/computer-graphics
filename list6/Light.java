package list6;

public class Light {
    // light position and intensity for every RGB channel (ER, EG, EB)
    public Vector3D position, intensity;

    public Light(Vector3D position, Vector3D intensity)
    {
        this.position = position;
        this.intensity = intensity;
    }
}