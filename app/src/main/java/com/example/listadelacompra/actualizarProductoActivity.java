package com.example.listadelacompra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class actualizarProductoActivity extends AppCompatActivity {

    protected TextView texto1;
    protected EditText caja1;
    protected EditText caja2;
    protected EditText caja3;
    protected Button boton1;
    protected Button boton2;
    protected String paquete1="";
    protected String contenidoCaja1="";
    protected String contenidoCaja2="";
    protected String contenidoCaja3="";
    protected Bundle extras;
    protected GestorBaseDatos gbd;

    protected Producto pro;

    protected Intent pasarPantalla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.boton1_actualizar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_actu);
        caja1 = (EditText) findViewById(R.id.caja1_actu);
        caja2 = (EditText) findViewById(R.id.caja2_actu);
        caja3 = (EditText) findViewById(R.id.caja3_actu);
        boton1 = (Button) findViewById(R.id.boton1_actu);
        boton2 = (Button) findViewById(R.id.boton2_actu);

        gbd = new GestorBaseDatos(this);

        extras = getIntent().getExtras();
        if (extras != null)
        {
            paquete1 = extras.getString("ID");
            //Toast.makeText(this, "Paquete recibido es: "+paquete1, Toast.LENGTH_SHORT).show();
            pro = gbd.obtenerProducto(paquete1);
            caja1.setText(pro.getNombre());
            caja2.setText(""+pro.getPrecio());
            caja3.setText(""+pro.getCantidad());


            boton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contenidoCaja1 = caja1.getText().toString();
                    contenidoCaja2 = caja2.getText().toString();
                    contenidoCaja3 = caja3.getText().toString();
                    if(contenidoCaja1.equalsIgnoreCase(""))
                    {
                        Toast.makeText(actualizarProductoActivity.this, "Debe de rellenar la caja de texto", Toast.LENGTH_SHORT).show();
                    }else {
                        caja1.setText("");
                        caja2.setText("");
                        caja3.setText("");
                        if (gbd.actualizarProducto(Integer.parseInt(paquete1),contenidoCaja1, Float.parseFloat(contenidoCaja2),Integer.parseInt(contenidoCaja3)))
                        {
                            Toast.makeText(actualizarProductoActivity.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                            pasarPantalla = new Intent(actualizarProductoActivity.this, MainActivity.class);
                            startActivity(pasarPantalla);
                        }
                        else{
                            Toast.makeText(actualizarProductoActivity.this, "Se ha producido un error al actualizar el producto", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });

            boton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pasarPantalla = new Intent(actualizarProductoActivity.this, MainActivity.class);
                    startActivity(pasarPantalla);
                }
            });
        }
        else
        {
            Toast.makeText(this, "No se han recibido paquetes", Toast.LENGTH_SHORT).show();
        }

    }
}