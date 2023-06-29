package com.example.malagafree.TicketsYNoticias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream
import java.io.InputStream

class Ticket : AppCompatActivity() {

    private lateinit var nombreProductoEditText: TextInputEditText
    private lateinit var nombreEmpresaEditText: TextInputEditText
    private lateinit var codigoIdentificadorEditText: TextInputEditText
    private lateinit var btnInfoIdentificador: ImageButton

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        ModoInversivo.setImmersiveMode(this)

        nombreProductoEditText = findViewById(R.id.textNombreProducto_editText)
        nombreEmpresaEditText = findViewById(R.id.textNombreEmpresa_editText)
        codigoIdentificadorEditText = findViewById(R.id.textCodigoIdentidicador_editText)
        btnInfoIdentificador = findViewById(R.id.btnInfoIdentificacion)

        btnInfoIdentificador.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Identificador")

            // Configurar la imagen
            val imageView = ImageView(this)
            imageView.setImageResource(R.drawable.codigodebarra)
            builder.setView(imageView)

            // Configurar el botón "Aceptar" del diálogo
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss() // Cerrar el diálogo al pulsar el botón "Aceptar"
            }

            val dialog = builder.create()
            dialog.show()
        }


        val botonLogin: Button = findViewById(R.id.botonLogin)
        botonLogin.setOnClickListener {
            if (camposRellenos()) {
                guardarInformacion()
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
    }

    private fun camposRellenos(): Boolean {
        val nombreProducto = nombreProductoEditText.text.toString()
        val nombreEmpresa = nombreEmpresaEditText.text.toString()
        val codigoIdentificador = codigoIdentificadorEditText.text.toString()

        return nombreProducto.isNotEmpty() && nombreEmpresa.isNotEmpty() && codigoIdentificador.isNotEmpty()
    }

    private fun guardarInformacion() {
        val nombreProducto = nombreProductoEditText.text.toString()
        val nombreEmpresa = nombreEmpresaEditText.text.toString()
        val codigoIdentificador = codigoIdentificadorEditText.text.toString()

        val informacion = "Nombre del Producto: $nombreProducto\n" +
                "Nombre de la Empresa: $nombreEmpresa\n" +
                "Identificador del Producto: $codigoIdentificador"

        // Crear un archivo de texto en Firebase Storage
        val storageReference = firebaseStorage.reference
        val archivoReferencia = storageReference.child("informacion_ticket_$nombreProducto.txt")

        val datos: ByteArray = informacion.toByteArray()
        val inputStream: InputStream = ByteArrayInputStream(datos)

        archivoReferencia.putStream(inputStream)
            .addOnSuccessListener {
                Toast.makeText(this, "Archivo creado exitosamente", Toast.LENGTH_SHORT).show()
                nombreProductoEditText.setText("")
                nombreEmpresaEditText.setText("")
                codigoIdentificadorEditText.setText("")
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al crear el archivo", Toast.LENGTH_SHORT).show()
            }
    }
}
