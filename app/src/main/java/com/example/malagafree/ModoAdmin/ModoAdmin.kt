package com.example.malagafree.ModoAdmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.carlosml.malagafree.R
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.TicketsYNoticias.Noticias
import com.example.malagafree.TicketsYNoticias.Ticket

class ModoAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modo_admin)

        ModoInversivo.setImmersiveMode(this)

        val btnProducto : Button = findViewById(R.id.btnCrearProducto)
        val btnLocales : Button = findViewById(R.id.btnCrearLocal)

        btnProducto.setOnClickListener {

            val intent = Intent (this, CrearProducto::class.java)
            this.startActivity(intent)

        }

        btnLocales.setOnClickListener {

            val intent = Intent (this, CrearLocal::class.java)
            this.startActivity(intent)

        }
    }
}