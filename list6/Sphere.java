package list6;

public class Sphere {
    public double radius;
    public Vector3D center;
    public Material material;

    public Sphere(double radius, Vector3D center, Material material)
    {
        this.center = center;
        this.material = material;
        this.radius = radius;
    }
}