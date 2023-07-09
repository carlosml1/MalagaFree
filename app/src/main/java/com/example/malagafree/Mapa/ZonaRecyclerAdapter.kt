package com.example.malagafree.Mapa

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.malagafree.R
import com.google.firebase.ktx.Firebase
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.storage.ktx.storage
import java.util.Locale


class ZonaRecyclerAdapter(var zonas: MutableList<Zona>) :
    RecyclerView.Adapter<ZonaRecyclerAdapter.ZonaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZonaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_zonas, parent, false)
        return ZonaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ZonaViewHolder, position: Int) {
        val zona = zonas[position]
        holder.bind(zona)
    }

    override fun getItemCount(): Int {
        return zonas.size
    }

    inner class ZonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardZona: CardView = itemView.findViewById(R.id.card_zonas)
        private val mostrarNombre: TextView = itemView.findViewById(R.id.mostrarNombre)
        private val mostrarNumero: TextView = itemView.findViewById(R.id.mostrarNumero)
        private val mostrarUbicacion: TextView = itemView.findViewById(R.id.mostrarUbicacion)
        private val mostrarHorario: TextView = itemView.findViewById(R.id.mostrarHorario)
        private val mostrarAcesible: TextView = itemView.findViewById(R.id.mostrarAccesibilidad)
        private val mostarImagen: ImageView = itemView.findViewById(R.id.mostarImagen)
        private val btnUbicacion: ImageButton = itemView.findViewById(R.id.btnUbicacion)
        private val btnCarta: ImageButton = itemView.findViewById(R.id.btnCarta)
        private val btnLlamar: ImageButton = itemView.findViewById(R.id.btnNumero)

        @SuppressLint("DiscouragedApi", "ClickableViewAccessibility")
        fun bind(zona: Zona) {
            mostrarNombre.text = zona.nombre
            mostrarNumero.text = zona.numero.toString()
            mostrarHorario.text = zona.horario
            mostrarUbicacion.text = zona.ubicacion
            mostrarAcesible.text = zona.accesible

            btnUbicacion.setOnClickListener {
                val coordenadas = "${zona.latitud}, ${zona.longitud}"
                abrirMapa(coordenadas)
            }

            btnCarta.setOnClickListener {
                val url = zona.carta
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                itemView.context.startActivity(intent)
            }

            btnLlamar.setOnClickListener {
                val phoneNumber = zona.numero
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$phoneNumber")
                itemView.context.startActivity(dialIntent)
            }

            val numero = convertirNumeroATexto(zona.id.toInt())
            val nombreImagen = zona.zona.toLowerCase() + numero
            val storageRef = Firebase.storage.reference.child("Fotos/$nombreImagen.PNG")

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                // El archivo se ha descargado exitosamente, puedes cargar la imagen usando la URI obtenida
                Glide.with(mostarImagen.context)
                    .load(uri)
                    .into(mostarImagen)
            }.addOnFailureListener { exception ->
                // Ocurrió un error al descargar el archivo
                // Maneja el error según tus necesidades
            }


            mostarImagen.setOnClickListener {
                val popupView = LayoutInflater.from(mostarImagen.context).inflate(R.layout.dialog_imagen, null)

                val popupWindow = PopupWindow(
                    popupView,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )

                val imagenDialog = popupView.findViewById<ImageView>(R.id.imagenDialog)
                val imagenTituloDialog = popupView.findViewById<TextView>(R.id.tituloZonaDialog)

                val numero = convertirNumeroATexto(zona.id.toInt())
                val nombreImagen = zona.zona.toLowerCase() + numero
                imagenTituloDialog.text = zona.nombre
                val storageReference = Firebase.storage.reference.child("Fotos/$nombreImagen.PNG")

                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    // La URL de descarga se ha obtenido correctamente
                    Glide.with(mostarImagen.context)
                        .load(uri)
                        .into(object : CustomTarget<Drawable>() {
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                imagenDialog.setImageDrawable(resource)

                                // Configura las propiedades de la ventana emergente
                                popupWindow.isFocusable = true
                                popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Fondo transparente
                                popupWindow.elevation = convertDpToPixel(5, mostarImagen.context).toFloat() // Sombra
                                popupWindow.animationStyle = R.style.PopupAnimation
                                popupWindow.showAtLocation(mostarImagen, Gravity.CENTER, 0, 0)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                // Maneja la limpieza de la carga si es necesario
                            }
                        })
                }.addOnFailureListener { exception ->
                    // Ocurrió un error al obtener la URL de descarga desde Firebase Storage
                    // Maneja el error según tus necesidades
                }


            }

            when (zona.establecimiento){
                "Bar" ->  cardZona.setCardBackgroundColor(itemView.context.getColor(R.color.verde_claro))
                "Restaurante" ->  cardZona.setCardBackgroundColor(itemView.context.getColor(R.color.beige))
                "Heladeria" ->  cardZona.setCardBackgroundColor(itemView.context.getColor(R.color.morado_claro))
            }



        }

        // Función para convertir dp a píxeles
        fun convertDpToPixel(dp: Int, context: Context): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }

        fun convertirNumeroATexto(numero: Int): String {
            val numerosTexto = arrayOf(
                "cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez",
                "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho",
                "diecinueve", "veinte", "veintiuno", "veintidós", "veintitrés", "veinticuatro",
                "veinticinco", "veintiséis", "veintisiete", "veintiocho", "veintinueve", "treinta",
                "treinta y uno", "treinta y dos", "treinta y tres", "treinta y cuatro", "treinta y cinco",
                "treinta y seis", "treinta y siete", "treinta y ocho", "treinta y nueve", "cuarenta",
                "cuarenta y uno", "cuarenta y dos", "cuarenta y tres", "cuarenta y cuatro", "cuarenta y cinco",
                "cuarenta y seis", "cuarenta y siete", "cuarenta y ocho", "cuarenta y nueve", "cincuenta",
                "cincuenta y uno", "cincuenta y dos", "cincuenta y tres", "cincuenta y cuatro",
                "cincuenta y cinco", "cincuenta y seis", "cincuenta y siete", "cincuenta y ocho",
                "cincuenta y nueve", "sesenta", "sesenta y uno", "sesenta y dos", "sesenta y tres",
                "sesenta y cuatro", "sesenta y cinco", "sesenta y seis", "sesenta y siete", "sesenta y ocho",
                "sesenta y nueve", "setenta", "setenta y uno", "setenta y dos", "setenta y tres",
                "setenta y cuatro", "setenta y cinco", "setenta y seis", "setenta y siete", "setenta y ocho",
                "setenta y nueve", "ochenta", "ochenta y uno", "ochenta y dos", "ochenta y tres",
                "ochenta y cuatro", "ochenta y cinco", "ochenta y seis", "ochenta y siete", "ochenta y ocho",
                "ochenta y nueve", "noventa", "noventa y uno", "noventa y dos", "noventa y tres",
                "noventa y cuatro", "noventa y cinco", "noventa y seis", "noventa y siete", "noventa y ocho",
                "noventa y nueve", "cien"
            )

            return if (numero in 0..100) {
                numerosTexto[numero]
            } else {
                "Número fuera de rango"
            }

        }

        @SuppressLint("WrongConstant", "QueryPermissionsNeeded")
        private fun abrirMapa(ubicacion: String) {
            val coordenadas = ubicacion.split(", ")
            val latitud = coordenadas[0].toDoubleOrNull()
            val longitud = coordenadas[1].toDoubleOrNull()

            if (latitud != null && longitud != null) {
                val uri = "geo:$latitud,$longitud"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                intent.setPackage("com.google.android.apps.maps")
                // Verificar si hay alguna aplicación que pueda manejar la intención
                val activityInfo = intent.resolveActivityInfo(itemView.context.packageManager, intent.flags)
                if (activityInfo != null && activityInfo.exported) {
                    // La aplicación de Google Maps está instalada, abrir el mapa directamente
                    itemView.context.startActivity(intent)
                } else {
                    // La aplicación de Google Maps no está instalada, abrir el mapa en el navegador
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=$latitud,$longitud"))
                    itemView.context.startActivity(webIntent)
                }
            } else {
                // Mostrar un mensaje de error si las coordenadas no son válidas
                Toast.makeText(itemView.context, "Coordenadas de ubicación inválidas", Toast.LENGTH_SHORT).show()
            }
        }


    }

}

