package es.ieslvareda.model;

import java.sql.Date;

public class Vehiculo {

    private String matricula;
    private int preciohora;
    private String marca;
    private String descripcion;
    private String color;
    private int bateria;
    private Date fechaadq;
    private String estado;
    private int idCarnet;
    private Date changedTS;
    private String changedBy;

    public Vehiculo(String matricula, int preciohora, String marca, String descripcion, String color, int bateria, Date fechaadq, String estado, int idCarnet, Date changedTS, String changedBy) {
        this.matricula = matricula;
        this.preciohora = preciohora;
        this.marca = marca;
        this.descripcion=descripcion;
        this.color = color;
        this.bateria = bateria;
        this.fechaadq = fechaadq;
        this.estado = estado;
        this.idCarnet = idCarnet;
        this.changedTS = changedTS;
        this.changedBy = changedBy;
    }



    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getPreciohora() {
        return preciohora;
    }

    public void setPreciohora(int preciohora) {
        this.preciohora = preciohora;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBateria() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public Date getFechaadq() {
        return fechaadq;
    }

    public void setFechaadq(Date fechaadq) {
        this.fechaadq = fechaadq;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCarnet() {
        return idCarnet;
    }

    public void setIdCarnet(int idCarnet) {
        this.idCarnet = idCarnet;
    }

    public Date getChangedTS() {
        return changedTS;
    }

    public void setChangedTS(Date changedTS) {
        this.changedTS = changedTS;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "matricula='" + matricula + '\'' +
                ", preciohora=" + preciohora +
                ", marca='" + marca + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", color='" + color + '\'' +
                ", bateria=" + bateria +
                ", fechaadq=" + fechaadq +
                ", estado='" + estado + '\'' +
                ", idCarnet=" + idCarnet +
                ", changedTS=" + changedTS +
                ", changedBy='" + changedBy + '\'' +
                '}';
    }
}
