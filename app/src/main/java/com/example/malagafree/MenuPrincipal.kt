package com.example.malagafree

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.carlosml.malagafree.R
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.Componentes.Sponsored
import com.example.malagafree.Componentes.Suscripcion
import com.example.malagafree.Mapa.ZonasMenuMapa
import com.example.malagafree.Productos.BuscarProductos
import com.example.malagafree.Productos.MenuProductos
import com.example.malagafree.Productos.RecyclerProductos
import com.example.malagafree.TicketsYNoticias.Ticket

import java.util.*


class MenuPrincipal : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        ModoInversivo.setImmersiveMode(this)

        val btnIrMapa: Button = findViewById(R.id.btnMapa)
        val btnIrProducto: Button = findViewById(R.id.btnProductos)
        val btnIrResNoti: Button = findViewById(R.id.btnReseNoti)
        val btnRapidoBus: ImageView = findViewById(R.id.btnRapidoBuscar)
        val btnRapidoScn: ImageView = findViewById(R.id.btnRapidoScanner)
        val lytBuscar: ConstraintLayout = findViewById(R.id.lytBtnBuscar)
        val lytScanner: ConstraintLayout = findViewById(R.id.lytBtnScanner)
        val img: ImageView = findViewById(R.id.imageView)
        val btnMenu: ImageButton = findViewById(R.id.menu)
        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
        val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")


        // Solicitar permiso de ubicación al hacer clic en el botón del mapa
        btnIrMapa.setOnClickListener {
            //requestLocationPermission()
            val intent = Intent (this, ZonasMenuMapa::class.java)
            this.startActivity(intent)
        }

        btnIrProducto.setOnClickListener {
            val intent = Intent(this, MenuProductos::class.java)
            startActivity(intent)
        }

        btnIrResNoti.setOnClickListener {
            // TODO: Esto está en revisión para mirar cómo hacer las noticias
            /*val intent = Intent(this, MenuTicketsYNoticias::class.java)
            startActivity(intent)*/
            val intent = Intent (this, Ticket::class.java)
            this.startActivity(intent)
        }

        btnRapidoBus.setOnClickListener {
            val intent = Intent(this, RecyclerProductos::class.java)
            startActivity(intent)
        }

        btnRapidoScn.setOnClickListener {
            val intent = Intent(this, BuscarProductos::class.java)
            startActivity(intent)
        }

        btnMenu.setOnClickListener { showPopupMenu(it) }


        when (opcionSeleccionada) {
            " Acromatía" -> {
                btnIrMapa.setBackgroundColor(getColor(R.color.negro_claro))
                btnIrProducto.setBackgroundColor(getColor(R.color.negro_claro))
                btnIrResNoti.setBackgroundColor(getColor(R.color.negro_claro))
                lytBuscar.setBackgroundColor(getColor(R.color.black))
                lytScanner.setBackgroundColor(getColor(R.color.black))
                img.setImageDrawable(getDrawable(R.drawable.iconoappnegroblanco))
                btnRapidoBus.setImageDrawable(getDrawable(R.drawable.ic_icono_lupa_blanco))
                btnRapidoBus.setBackgroundColor(getColor(R.color.negro_claro))
                btnRapidoScn.setImageDrawable(getDrawable(R.drawable.barcode_blanco))
                btnMenu.setImageDrawable(getDrawable(R.drawable.ic_icono_menu_desp_negro))
            }
            else -> {
                btnIrMapa.setBackgroundColor(getColor(R.color.azul))
                btnIrProducto.setBackgroundColor(getColor(R.color.azul))
                btnIrResNoti.setBackgroundColor(getColor(R.color.azul))
                lytBuscar.setBackgroundColor(getColor(R.color.azul))
                lytScanner.setBackgroundColor(getColor(R.color.azul))
                img.setImageDrawable(getDrawable(R.drawable.iconoapp))
                btnRapidoBus.setBackgroundColor(getColor(R.color.white))
                btnRapidoBus.setImageDrawable(getDrawable(R.drawable.ic_icono_lupa))
                btnRapidoScn.setImageDrawable(getDrawable(R.drawable.barcode))
                btnMenu.setImageDrawable(getDrawable(R.drawable.ic_icono_menu_desp))
            }
        }
    }

    private fun showPopupMenu(anchorView: android.view.View) {
        val popupMenu = PopupMenu(ContextThemeWrapper(this, R.style.MyPopupMenuStyle), anchorView)
        popupMenu.inflate(R.menu.menu_principal)

        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)


        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.opcionDaltonico -> {
                    val opcionesDaltonico = arrayListOf(" Normal", " Protanopia", " Deuteranopia", " Tritanopia", " Acromatía")
                    val builder = AlertDialog.Builder(this, R.style.AlertDialogStyle) // Aplicar el estilo personalizado
                    builder.setTitle("Selecciona una opción")
                    builder.setItems(opcionesDaltonico.toTypedArray()) { _, which ->
                        val selectedOption = opcionesDaltonico[which]
                        val editor: SharedPreferences.Editor = preferences.edit()
                        editor.putString("opcionSeleccionada", selectedOption)
                        editor.apply()
                        recreate()
                    }
                    builder.setNegativeButton("Cancelar", null)

                    val dialog = builder.create()
                    dialog.setCanceledOnTouchOutside(false) // No se puede hacer clic fuera del diálogo
                    dialog.show()
                    true
                }
                R.id.Sponsored -> {
                    val intent = Intent (this, Sponsored::class.java)
                    this.startActivity(intent)
                    true
                }
                R.id.Suscripcion -> {
                    val intent = Intent (this, Suscripcion::class.java)
                    this.startActivity(intent)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }


/*
    // Método para solicitar el permiso de ubicación en tiempo de ejecución
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            checkLocationPermission()
        }
    }

    // Método para verificar si se tiene el permiso de ubicación y obtener la ubicación actual
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // El permiso de ubicación fue denegado
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        } else {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            location?.let {
                val latitude = it.latitude
                val longitude = it.longitude
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val country = addresses[0].countryName
                    if (country == "España") {
                        // Está en España
                        val city = addresses[0].subAdminArea

                        if(city == "Málaga"){
                            //val intent = Intent (this, MenuMapa::class.java)
                            intent.putExtra("ciudad",city)
                            this.startActivity(intent)
                        }else{
                            Toast.makeText(this, "Esta funcion ahora mismo solo va en $city", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        // No está en España
                        Toast.makeText(this, "No funciona fuera de España", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // No se pudo obtener la ubicación
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                // No se pudo obtener la ubicación
                Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Método para manejar la respuesta de la solicitud de permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationPermission()
            } else {
                Toast.makeText(
                    this,
                    "Permiso de ubicación denegado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }*/
}
