package list6;

import static java.lang.Math.sqrt;

public class Vector3D {
    public final double x;
    public final double y;
    public final double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


//Punkty w przestrzeni 3D, wektory kierunkowe, kolory RGB (xyz)



    // dodawanie wektorow
    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

//    odejmowanie wektorow
    public Vector3D substr(Vector3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }
// mnozenie przez skalar
    public Vector3D multiply(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

//    mnozenie skladowe wektorow
    public Vector3D multiply(Vector3D other) {
        return new Vector3D(this.x * other.x, this.y * other.y, this.z * other.z);
    }

//    iloczyn skalarny
    public double dot(Vector3D other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

//    normalizacja wektor jednostkowy
    public Vector3D normalize() {
        double len = length();      // in case of the sphere this is radius
        return new Vector3D(this.x / len, this.y / len, this.z / len);
    }

    // Długość wektora
    public double length() {
        return sqrt(this.dot(this));
    }

    // Ograniczenie wartości do zakresu [0, max]
    public Vector3D clamp(double max) {
        return new Vector3D(
                clampValue(this.x, 0.0, max),
                clampValue(this.y, 0.0, max),
                clampValue(this.z, 0.0, max)
        );
    }

    private static double clampValue(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}