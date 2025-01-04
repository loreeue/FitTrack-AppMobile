package com.ldm.practica2;

public class Logro {

    private String titulo;
    private String descripcion;
    private int icono;
    private boolean desbloqueado;
    private int sonido;

    public Logro(String titulo, String descripcion, int icono, int sonido) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.icono = icono;
        this.desbloqueado = desbloqueado;
        this.sonido = sonido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public int getSonido() {
        return sonido;
    }
}