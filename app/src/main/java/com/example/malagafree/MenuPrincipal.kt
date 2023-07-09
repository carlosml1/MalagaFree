package com.example.malagafree

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.Mapa.ZonasMenuMapa
import com.example.malagafree.Productos.BuscarProductos
import com.example.malagafree.Productos.MenuProductos
import com.example.malagafree.Productos.RecyclerProductos
import com.example.malagafree.TicketsYNoticias.MenuTicketsYNoticias
import com.example.malagafree.TicketsYNoticias.Ticket
import java.util.*

class MenuPrincipal : AppCompatActivity() {
    // Identificador para solicitar el permiso de ubicación
    private val LOCATION_PERMISSION_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        ModoInversivo.setImmersiveMode(this)

        val btnIrMapa: Button = findViewById(R.id.btnMapa)
        val btnIrProducto: Button = findViewById(R.id.btnProductos)
        val btnIrResNoti: Button = findViewById(R.id.btnReseNoti)
        val btnRapidoBus: ImageView = findViewById(R.id.btnRapidoBuscar)
        val btnRapidoScn: ImageView = findViewById(R.id.btnRapidoScanner)

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
