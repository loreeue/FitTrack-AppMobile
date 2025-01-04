package com.ldm.practica2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividades")
public class Actividad {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String tipo;
    private double distancia;
    private String tiempo;
    private double velocidadMaxima; // Para ciclismo
    private String tipoAgua; // Para nadar

    public Actividad(String tipo, double distancia, String tiempo, double velocidadMaxima, String tipoAgua) {
        this.tipo = tipo;
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.velocidadMaxima = velocidadMaxima;
        this.tipoAgua = tipoAgua;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public double getVelocidadMaxima() { return velocidadMaxima; }

    public void setVelocidadMaxima(double velocidadMaxima) { this.velocidadMaxima = velocidadMaxima; }

    public String getTipoAgua() { return tipoAgua; }

    public void setTipoAgua(String tipoAgua) { this.tipoAgua = tipoAgua; }
}