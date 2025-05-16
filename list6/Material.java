package list6;

public class Material {

    Vector3D kd, ks, ka, sl;
    double g;

    public Material(Vector3D kd, Vector3D ks, Vector3D ka, Vector3D sl, double g) {
        this.kd = kd;   // diffuse reflection coefficients: kdR, kdG, kdB - kolor powierzchni
        this.ks = ks;   // specular reflection coefficients: ksR, ksG, ksB - polysk
        this.ka = ka;   // ambient light diffuse reflection coefficients: kaR, kaG, kaB - wsp. swiatla otoczenia
        this.sl = sl;   // self luminance RGB samoswiecenie
        this.g = g;     // shiness - wykladnik phonga wiekszy to ostrzejsze refleksy
    }

}