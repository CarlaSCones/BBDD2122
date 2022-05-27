package es.ieslvareda.model;

public class Bicicleta {

    private String matricula;
    private String tipo;

    public Bicicleta(String matricula, String tipo) {
        this.matricula = matricula;
        this.tipo = tipo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Bicicleta{" +
                "matricula='" + matricula + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
