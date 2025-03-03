package com.example.listadelacompra;

import android.os.Bundle;
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
    protected String contenidoCaja1="";
    protected String contenidoCaja2="";
    protected String contenidoCaja3="";
    protected GestorBaseDatos gdb;
        protected String paquete1="";
    protected Bundle extras;

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

        extras = getIntent().getExtras();
        if (extras != null)
        {
            paquete1 = extras.getString("ID");
            Toast.makeText(this, "este"+paquete1, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "No se han recibido paquetes", Toast.LENGTH_SHORT).show();
        }
        
    }
}