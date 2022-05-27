package es.ieslvareda.model;

public class Patinete {

    private String matricula;
    private int numRuedas;
    private int tamanyo;

    public Patinete(String matricula, int numRuedas, int tamanyo) {
        this.matricula = matricula;
        this.numRuedas = numRuedas;
        this.tamanyo = tamanyo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getNumRuedas() {
        return numRuedas;
    }

    public void setNumRuedas(int numRuedas) {
        this.numRuedas = numRuedas;
    }

    public int getTamanyo() {
        return tamanyo;
    }

    public void setTamanyo(int tamanyo) {
        this.tamanyo = tamanyo;
    }

    @Override
    public String toString() {
        return "Patinete{" +
                "matricula='" + matricula + '\'' +
                ", numRuedas=" + numRuedas + '\'' +
                ", tamaño='" + tamanyo +
                '}';
    }
}
