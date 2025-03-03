package com.example.listadelacompra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GestorBaseDatos extends SQLiteOpenHelper {


    public GestorBaseDatos(@Nullable Context context) {
        super(context, "listaCompra", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio FLOAT, cantidad INTEGER)");
        db.execSQL("INSERT INTO productos VALUES(null,'producto ej 1',30,5)");
        db.execSQL("INSERT INTO productos VALUES(null,'producto ej 2',20,10)");
        db.execSQL("INSERT INTO productos VALUES(null,'producto ej 3',10,6)");
        db.execSQL("INSERT INTO productos VALUES(null,'producto ej 4',15,8)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean borrarProducto (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM productos WHERE id="+id);

        if(existeIdProductos(id)){
            return false;

        }
        return true;

    }


    public boolean insertarProducto(String nombre, float precio, int cantidad)
    {
        SQLiteDatabase db = this.getWritableDatabase();//Aqui en este caso voy a insertar
        if(!existeProductos(nombre))
        {
            db.execSQL("INSERT INTO productos VALUES(null,'"+nombre+"',"+precio+","+cantidad+")");

            return true;
        }
        return false;
    }

    public Producto obtenerProducto (String id)
    {
        Producto pro; //Defino el objeto

        if (existeProductos(id)) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM productos WHERE id=" + id, null);

            if (cur != null) {

                cur.moveToFirst(); //Voy a la primera fila y entro en el bucle
                pro = new Producto(cur.getInt(0), cur.getString(1), cur.getFloat(2),cur.getInt(3));
                return pro;
                
            }
        }

        return null;
    }



    public ArrayList<String> obtenerProductos()
    {
        ArrayList<String> productos= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase(); //En este caso leo
        Cursor cur = db.rawQuery("SELECT * FROM productos",null);
        if(cur!=null){

            cur.moveToFirst(); //Voy a la primera fila y entro en el bucle
            while(!cur.isAfterLast())
            {
                //Codigo para obtener la informacion
                productos.add(cur.getString(0) + ".-" + cur.getString(1) + " (" + cur.getString(2) + " €) " + "(" + cur.getString(3) + " unidades)");


                cur.moveToNext();
            }
            cur.close();
        }

        return productos;
    }

    public boolean existeProductos(String nombre)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //En este caso leo
        Cursor cur = db.rawQuery("SELECT * FROM productos WHERE nombre= '"+nombre+"'",null);
        if(cur!=null)
        {
           cur.moveToLast();
           if (cur.getCount()>0){
               return true;
           }
        }
        return false;
    }

    public boolean existeIdProductos(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //En este caso leo
        Cursor cur = db.rawQuery("SELECT * FROM productos WHERE id= '"+id+"'",null);
        if(cur!=null)
        {
            cur.moveToLast();
            if (cur.getCount()>0){
                return true;
            }
        }
        return false;
    }
}
