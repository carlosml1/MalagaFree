package com.example.malagafree.Productos

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malagafree.Componentes.ModoInversivo
import com.example.malagafree.R
import com.google.firebase.firestore.FirebaseFirestore

class RecyclerProductos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductosAdapter
    private val productosList: ArrayList<String> = ArrayList()
    private var filteredList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_productos)

        ModoInversivo.setImmersiveMode(this)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ProductosAdapter(productosList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = FirebaseFirestore.getInstance()
        val productosRef = db.collection("Productos")

        productosRef.get().addOnSuccessListener { result ->
            for (document in result) {
                val nombre = document.getString("Nombre")
                val nombreEmpresa = document.getString("NombreEmpresa")
                val tipoProducto = document.getString("tipoProducto")
                if (nombre != null && nombreEmpresa != null && tipoProducto != null) {
                    val producto = "$tipoProducto: $nombre - $nombreEmpresa"
                    productosList.add(producto)
                }
            }
            adapter.notifyDataSetChanged()
            filteredList.addAll(productosList)
        }.addOnFailureListener { exception ->
            // Manejo de errores en caso de que la consulta falle
        }

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementación aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filtrar la lista de productos en función del texto ingresado
                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementación aquí
            }
        })

        adapter.setOnItemClickListener { nombreEmpresa, nombre, ok ->
            showCustomDialog(nombreEmpresa, nombre, ok)
        }

    }

    private fun filterProducts(query: String) {
        filteredList.clear()
        for (product in productosList) {
            if (product.contains(query, ignoreCase = true)) {
                filteredList.add(product)
            }
        }
        adapter.filterList(filteredList)
    }

    private fun showCustomDialog(nombreEmpresa: String?, nombre: String?, ok: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Producto Encontrado")

        val message = "\n\nEmpresa: $nombreEmpresa\n\nNombre del Producto: $nombre\n\nLibre de Gluten: $ok\n\n"
        val spannableMessage = SpannableString(message)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableMessage.setSpan(boldSpan, 2, message.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder.setMessage(spannableMessage)

        val dialog = builder.create()

        dialog.setOnShowListener {
            dialog.window?.setBackgroundDrawableResource(R.color.azul)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.BLACK)
        }

        dialog.show()
    }
}

