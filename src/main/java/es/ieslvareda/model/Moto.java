package es.ieslvareda.model;

public class Moto {

    private String matricula;
    private int velocidadMax;
    private int cilindrada;

    public Moto(String matricula, int velocidadMax, int cilindrada) {
        this.matricula = matricula;
        this.velocidadMax = velocidadMax;
        this.cilindrada = cilindrada;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getVelocidadMax() {
        return velocidadMax;
    }

    public void setVelocidadMax(int velocidadMax) {
        this.velocidadMax = velocidadMax;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    @Override
    public String toString() {
        return "Moto{" +
                "matricula='" + matricula + '\'' +
                ", velocidadMax=" + velocidadMax +
                ", cilindrada='" + cilindrada + '\'' +
                '}';
    }
}
