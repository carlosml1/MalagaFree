package com.example.malagafree.Productos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.carlosml.malagafree.R
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.ProductosGenericos.ProductosGenericosPantalla

class MenuProductos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_productos)

        ModoInversivo.setImmersiveMode(this)

        val btnScanner : Button = findViewById(R.id.btnPorCodigoDeBarra)
        val btnNombre : Button = findViewById(R.id.btnPorNombreProducto)
        val btnGenerico : Button = findViewById(R.id.btnPorProductosGenericos)

        btnGenerico.setOnClickListener {
            val intent = Intent (this, ProductosGenericosPantalla::class.java)
            this.startActivity(intent)
        }

        btnScanner.setOnClickListener {

            val intent = Intent (this, BuscarProductos::class.java)
            this.startActivity(intent)

        }

        btnNombre.setOnClickListener {

            val intent = Intent (this, RecyclerProductos::class.java)
            this.startActivity(intent)

        }

        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)

        when (preferences.getString("opcionSeleccionada", "")) {
            " Acromatía" -> {
               btnNombre.setBackgroundColor(getColor(R.color.negro_claro))
               btnScanner.setBackgroundColor(getColor(R.color.negro_claro))
                btnGenerico.setBackgroundColor(getColor(R.color.negro_claro))
            }
            else -> {
                btnNombre.setBackgroundColor(getColor(R.color.azul))
                btnScanner.setBackgroundColor(getColor(R.color.azul))
                btnGenerico.setBackgroundColor(getColor(R.color.azul))
            }
        }

    }
}