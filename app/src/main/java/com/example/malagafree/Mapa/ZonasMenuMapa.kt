package com.example.malagafree.Mapa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.R

class ZonasMenuMapa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zonas_menu_mapa)

        ModoInversivo.setImmersiveMode(this)
        var todasLasZonas : Array<String> = arrayOf("")

        /*val ciudad = intent.getStringExtra("ciudad")

        when (ciudad){
            "Málaga" -> todasLasZonas = arrayOf("Centro", "Málaga Este","Puerto de la Torre","Cruz de Humilladero", "Carretera De Cadiz","Churriana","Campanilla","Teatinos","Montes de Málaga")

        }*/

        todasLasZonas = arrayOf("Centro", "Málaga Este","Puerto de la Torre","Cruz de Humilladero", "Carretera De Cadiz","Churriana","Campanilla","Teatinos","Montes de Málaga")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerZonas)

        val adapter = ZonasAdapter(todasLasZonas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}