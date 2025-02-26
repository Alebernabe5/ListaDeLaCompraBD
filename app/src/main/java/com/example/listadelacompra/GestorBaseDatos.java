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

    public ArrayList<String> obtenerProductos()
    {
        ArrayList<String> productos= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM productos",null);
        if(cur!=null){

            cur.moveToFirst(); //Voy a la primera fila y entro en el bucle
            while(!cur.isAfterLast())
            {
                //Codigo para obtener la informacion
                productos.add(cur.getString(0) + "._" + cur.getString(1) + " (" + cur.getString(2) + " €) " + "(" + cur.getString(3) + " unidades)");


                cur.moveToNext();
            }
            cur.close();
        }
        db.close();
        return productos;
    }
}
