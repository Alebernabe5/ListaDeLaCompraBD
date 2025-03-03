package com.example.listadelacompra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    protected TextView texto1;
    protected EditText caja1;
    protected EditText caja2;
    protected EditText caja3;
    protected Button boton1;
    protected ListView lista1;

    protected String contenidoCaja1="";
    protected String contenidoCaja2="";
    protected String contenidoCaja3="";
    protected GestorBaseDatos gdb;

    protected ArrayList<String> listaProductos = new ArrayList<String>();
    protected ArrayAdapter<String> adaptador;
    protected String contenidoItem="";
    protected String[] partes;
    protected Intent pasarPantalla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.boton1_actualizar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_main);
        caja1 = (EditText) findViewById(R.id.caja1_main);
        caja2 = (EditText) findViewById(R.id.caja2_main);
        caja3 = (EditText) findViewById(R.id.caja3_main);
        boton1 = (Button) findViewById(R.id.boton1_main);
        lista1 = (ListView) findViewById(R.id.lista1_main);

        gdb = new GestorBaseDatos(this); //Aqui es donde creo la BBDD y las tablas y solo se va hacer una vez
        listaProductos = gdb.obtenerProductos();
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaProductos);
        lista1.setAdapter(adaptador);

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                contenidoItem = adapterView.getItemAtPosition(i).toString();

                partes = contenidoItem.split(".-");

                pasarPantalla =new Intent(MainActivity.this, actualizarProductoActivity.class);
                pasarPantalla.putExtra("ID", partes[0]); //Con esto mando el paquete que tiene dos partes
                startActivity(pasarPantalla);


            }
        });


        lista1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


               // Toast.makeText(MainActivity.this, "Contenido item" +contenidoItem, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Desea eliminar el producto "+partes[1]+"?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // START THE GAME!
                                if (gdb.borrarProducto(partes[0]))
                                {
                                    Toast.makeText(MainActivity.this, "Producto borrado correctamente", Toast.LENGTH_SHORT).show();
                                    adaptador.clear();
                                    listaProductos = gdb.obtenerProductos(); //Se va ha actualizar el arraylist tras la inserccion de los datos
                                    adaptador.addAll(listaProductos); //Actualizar nuevos datos
                                    adaptador.notifyDataSetChanged();//notifica que se han producido cambios
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "No se pudo borrar el producto", Toast.LENGTH_SHORT).show();

                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancels the dialog.
                            }
                        });
                // Create the AlertDialog object and return it.
                builder.create();
                builder.show();

                return true;
            }
        });

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contenidoCaja1 = caja1.getText().toString();
                contenidoCaja2 = caja2.getText().toString();
                contenidoCaja3 = caja3.getText().toString();
                if(contenidoCaja1.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this, "Debe de rellenar la caja de texto", Toast.LENGTH_SHORT).show();
                }else
                {
                    caja1.setText("");
                    caja2.setText("");
                    caja3.setText("");
                   if(gdb.insertarProducto(contenidoCaja1, Float.parseFloat(contenidoCaja2),Integer.parseInt(contenidoCaja3)))
                   {
                       Toast.makeText(MainActivity.this, "Producto añadido correctamente", Toast.LENGTH_SHORT).show();
                       adaptador.clear();
                       listaProductos = gdb.obtenerProductos(); //Se va ha actualizar el arraylist tras la inserccion de los datos
                       adaptador.addAll(listaProductos); //Actualizar nuevos datos
                       adaptador.notifyDataSetChanged();//notifica que se han producido cambios

                   }
                   else {
                       Toast.makeText(MainActivity.this, "No se pudo añadir el producto", Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });
    }
}