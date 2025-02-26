package com.example.listadelacompra;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    protected TextView texto1;
    protected EditText caja1;
    protected Button boton1;
    protected ListView lista1;

    protected String contenidoCaja1="";
    protected GestorBaseDatos gdb;

    protected ArrayList<String> listaProductos = new ArrayList<String>();
    protected ArrayAdapter<String> adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_main);
        caja1 = (EditText) findViewById(R.id.caja1_main);
        boton1 = (Button) findViewById(R.id.boton1_main);
        lista1 = (ListView) findViewById(R.id.lista1_main);

        gdb = new GestorBaseDatos(this); //Aqui es donde creo la BBDD y las tablas y solo se va hacer una vez
        listaProductos = gdb.obtenerProductos();
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaProductos);
        lista1.setAdapter(adaptador);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contenidoCaja1 = caja1.getText().toString();
                if(contenidoCaja1.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this, "Debe de rellenar la caja de texto", Toast.LENGTH_SHORT).show();
                }else
                {
                    caja1.setText("");
                }
            }
        });
    }
}