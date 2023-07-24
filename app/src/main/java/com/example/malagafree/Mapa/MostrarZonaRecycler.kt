package com.example.malagafree.Mapa

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
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
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_zona_recycler)
        val textTitulo: TextView = findViewById(R.id.tituloZona)
        val btnInfoZona: ImageButton = findViewById(R.id.btnInfoZona)

        ModoInversivo.setImmersiveMode(this)

        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()

        val nombreZona = intent.getStringExtra("nombreZona")

        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
        val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")

        if(opcionSeleccionada!!.contains("Acromatía")){
            textTitulo.setTextColor(getColor(R.color.negro_claro))
            btnInfoZona.setBackgroundColor(getColor(R.color.negro_claro))
        }else{
            textTitulo.setTextColor(getColor(R.color.azul))
            btnInfoZona.setBackgroundColor(getColor(R.color.azul))
        }

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
                    val establecimiento = documentSnapshot.getString("Establecimiento")

                    if (numero != null && latitud != null && longitud != null && horario != null && id != null && ubicacion != null && Accesibilidad != null && establecimiento != null) {
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
                            nombreZona.toString(),
                            establecimiento
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

        btnInfoZona.setOnClickListener {
            showDialog(this)
        }

        // Verificar si el diálogo ya se ha mostrado antes
        sharedPreferences = getSharedPreferences("dialog_preferences", Context.MODE_PRIVATE)
        val dialogShown = sharedPreferences.getBoolean("dialog_shown", false)
        if (!dialogShown) {
            // Mostrar el diálogo personalizado al iniciar la pantalla
            showDialog(this)
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

    // Función para mostrar el diálogo personalizado
    private fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
        val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")
        val layoutResId = when (opcionSeleccionada) {
            " Normal" -> R.layout.dialog_pref_restaurante_bar_heladeria
            " Protanopia" -> R.layout.dialog_pref_restaurante_bar_heladeria_protanopia // Replace with the layout resource ID for Protanopia
            " Deuteranopia" -> R.layout.dialog_pref_restaurante_bar_heladeria_deuteranopia // Replace with the layout resource ID for Deuteranopia
            " Tritanopia" -> R.layout.dialog_pref_restaurante_bar_heladeria_tritanopia
            " Acromatía" -> R.layout.dialog_pref_restaurante_bar_heladeria_acromatia // Replace with the layout resource ID for Acromatía
            else -> R.layout.dialog_pref_restaurante_bar_heladeria// Replace with the default layout resource ID
        }


        dialog.setContentView(layoutResId)

        // Configurar los elementos del diálogo
        val acceptButton = dialog.findViewById<Button>(R.id.btnAceptarDialog)
        acceptButton?.setOnClickListener {
            // Guardar en las preferencias compartidas que el diálogo se ha mostrado
            val editor = sharedPreferences.edit()
            editor.putBoolean("dialog_shown", true)
            editor.apply()

            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()
    }

}
