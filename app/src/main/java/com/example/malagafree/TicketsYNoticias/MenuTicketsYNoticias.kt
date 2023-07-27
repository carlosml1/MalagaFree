package com.example.malagafree.TicketsYNoticias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.carlosml.malagafree.R
import com.example.malagafree.Componentes.ModoInversivo

class MenuTicketsYNoticias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_resenas_ynoticias)

        ModoInversivo.setImmersiveMode(this)

        val btnTicket : Button = findViewById(R.id.btnCrearTicket)
        val btnNoticias : Button = findViewById(R.id.btnNoticias)

        btnTicket.setOnClickListener {

            val intent = Intent (this, Ticket::class.java)
            this.startActivity(intent)

        }

        btnNoticias.setOnClickListener {

            val intent = Intent (this, Noticias::class.java)
            this.startActivity(intent)

        }

    }
}