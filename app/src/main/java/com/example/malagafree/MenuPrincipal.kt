package com.example.malagafree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.Mapa.MenuMapa
import com.example.malagafree.Productos.BuscarProductos
import com.example.malagafree.Productos.MenuProductos
import com.example.malagafree.TicketsYNoticias.MenuTicketsYNoticias
import com.example.malagafree.TicketsYNoticias.Ticket

class MenuPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        ModoInversivo.setImmersiveMode(this)

        val btnIrMapa : Button = findViewById(R.id.btnMapa)
        val btnIrProducto : Button = findViewById(R.id.btnProductos)
        val btnIrResNoti : Button = findViewById(R.id.btnReseNoti)

        btnIrMapa.setOnClickListener {

            val intent = Intent (this, MenuMapa::class.java)
            this.startActivity(intent)

        }

        btnIrProducto.setOnClickListener {

            val intent = Intent (this, MenuProductos::class.java)
            this.startActivity(intent)

        }

        btnIrResNoti.setOnClickListener {

            //TODO esto esta en revision para mirar como hacer las noticias
            val intent = Intent (this, MenuTicketsYNoticias::class.java)
            this.startActivity(intent)

            //val intent = Intent (this, Ticket::class.java)
            //this.startActivity(intent)

        }

    }
}