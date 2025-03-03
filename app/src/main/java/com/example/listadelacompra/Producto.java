package com.example.listadelacompra;

public class Producto {


    protected int id;
    protected String nombre;
    protected float precio;
    protected int cantidad;


    public Producto (int i, String n, float p, int c) {
        this.id = i;
        this.nombre = n;
        this.precio = p;
        this.cantidad = c;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
