package com.example.malagafree.Mapa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class MostrarZonaRecycler : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var zonaAdapter: ZonaRecyclerAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_zona_recycler)
        val textTitulo: TextView = findViewById(R.id.tituloZona)

        ModoInversivo.setImmersiveMode(this)

        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()

        val nombreZona = intent.getStringExtra("nombreZona")

        textTitulo.text = ponerTituloZona(nombreZona.toString())

        recyclerView = findViewById(R.id.recyclerZona)
        recyclerView.layoutManager = LinearLayoutManager(this)
        zonaAdapter = ZonaRecyclerAdapter(mutableListOf())
        recyclerView.adapter = zonaAdapter

        // Obtener datos de Firebase Firestore
        val zonaRef = db.collection(nombreZona.toString())
        zonaRef.get().addOnSuccessListener { querySnapshot ->
            val zonas = mutableListOf<Zona>()
            for (documentSnapshot in querySnapshot.documents) {
                val nombre = documentSnapshot.id // Obtener el nombre del documento

                if (documentSnapshot.exists()) {
                    val numero = documentSnapshot.getLong("Numero")
                    val latitud = documentSnapshot.getString("Latitud")
                    val longitud = documentSnapshot.getString("Longitud")
                    val horario = documentSnapshot.getString("Horario")
                    val carta = documentSnapshot.getString("Carta")
                    val ubicacion = documentSnapshot.getString("Ubicacion")
                    val Accesibilidad = documentSnapshot.getString("Accesible")
                    val id = documentSnapshot.getLong("id")

                    if (numero != null && latitud != null && longitud != null && horario != null && id != null && ubicacion != null && Accesibilidad != null) {
                        val zona = Zona(
                            nombre,
                            numero,
                            latitud,
                            longitud,
                            horario,
                            carta.toString(),
                            id,
                            Accesibilidad,
                            ubicacion,
                            nombreZona.toString()
                        )
                        zonas.add(zona)
                    }
                }
            }

            zonaAdapter.zonas = zonas
            zonaAdapter.notifyDataSetChanged()
        }.addOnFailureListener {
            // Manejar errores
        }

    }

    fun ponerTituloZona(zona : String) : String{
        when(zona){
            "Centro" -> {
                return getString(R.string.restaurantes_bares_del_centro_de_m_laga)
            }
            "MalagaEste" -> {
                return getString(R.string.restaurantes_bares_de_MalagaEste_de_m_laga)
            }
            "PuertoDeLaTorre" -> {
                return getString(R.string.restaurantes_bares_del_PuertoDeLaTorre)
            }
            "CruzDeHumilladero" -> {
                return getString(R.string.restaurantes_bares_del_CruzDeHumilladero)
            }
            "CarreteraDeCadiz" -> {
                return getString(R.string.restaurantes_bares_del_CarreteraDeCadiz)
            }
            "Churriana" -> {
                return getString(R.string.restaurantes_bares_del_Churriana)
            }
            "Campanilla" -> {
                return getString(R.string.restaurantes_bares_del_Campanilla)
            }
            "Teatinos" -> {
                return getString(R.string.restaurantes_bares_del_Teatinos)
            }
            "MontesDeMalaga" -> {
                return getString(R.string.restaurantes_bares_del_MonstesDeMalaga)
            }
        }
        return ""
    }

}
