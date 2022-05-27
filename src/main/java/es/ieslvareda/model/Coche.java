package es.ieslvareda.model;

public class Coche {

    private String matricula;
    private int numPlazas;
    private int numPuertas;

    public Coche(String matricula, int numPlazas, int numPuertas) {
        this.matricula = matricula;
        this.numPlazas = numPlazas;
        this.numPuertas = numPuertas;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }

    public int getNumPuertas() {
        return numPuertas;
    }

    public void setNumPuertas(int numPuertas) {
        this.numPuertas = numPuertas;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "matricula='" + matricula + '\'' +
                ", numPlazas=" + numPlazas +
                ", numPuertas='" + numPuertas + '\'' +
                '}';
    }
}
