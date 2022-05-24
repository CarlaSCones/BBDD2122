package es.ieslvareda.model;

public class Vehiculo {

    private String matricula;
    private float preciohora;
    private String marca;
    private String color;

    public Vehiculo(String matricula, float preciohora, String marca, String color) {
        this.matricula = matricula;
        this.preciohora = preciohora;
        this.marca = marca;
        this.color = color;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public float getPreciohora() {
        return preciohora;
    }

    public void setPreciohora(float preciohora) {
        this.preciohora = preciohora;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "matricula='" + matricula + '\'' +
                ", preciohora=" + preciohora +
                ", marca='" + marca + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
