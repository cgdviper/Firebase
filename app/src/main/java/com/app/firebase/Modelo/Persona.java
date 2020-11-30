package com.app.firebase.Modelo;

public class Persona {
    private String Run;
    private String Nombre;
    private String Ciudad;
    private String Numero;

    public Persona(){}
    public Persona(String run, String nombre, String ciudad, String NUmero) {
        this.Run = run;
        this.Nombre = nombre;
        this.Ciudad = ciudad;
        this.Numero = NUmero;
    }

    public String getRun() {
        return Run;
    }

    public void setRun(String run) {
        Run = run;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getNUmero() {
        return Numero;
    }

    public void setNUmero(String NUmero) {
        this.Numero = NUmero;
    }


}
