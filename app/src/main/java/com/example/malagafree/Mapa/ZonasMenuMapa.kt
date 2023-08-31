package com.example.malagafree.Mapa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosml.malagafree.R
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.MapaInteractivo.MapaInteractivo

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
        val btnRapidoMapaInteractivo: ImageButton = findViewById(R.id.btnRapidoMapaInteractivo)
        val btnVolver: ImageButton = findViewById(R.id.btnVolverZonasMenuMapa)

        val adapter = ZonasAdapter(todasLasZonas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnRapidoMapaInteractivo.setOnClickListener {
            val intent = Intent(this, MapaInteractivo::class.java)
            finish()
            startActivity(intent)
        }

        btnVolver.setOnClickListener {
            onBackPressed()
        }
    }
}