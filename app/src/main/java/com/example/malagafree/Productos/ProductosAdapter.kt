package com.example.malagafree.Productos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.malagafree.R

class ProductosAdapter(private var productosList: List<String>) : RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productosList[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    fun filterList(filteredList: List<String>) {
        productosList = filteredList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (String?, String?, String?) -> Unit) {
        this.onItemClickListener = object : ProductosAdapter.OnItemClickListener {
            override fun onItemClick(nombreEmpresa: String?, nombre: String?, ok: String?) {
                listener(nombreEmpresa, nombre, ok)
            }
        }
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productoTextView: TextView = itemView.findViewById(R.id.productoTextView)

        @SuppressLint("SuspiciousIndentation")
        fun bind(producto: String) {
            productoTextView.text = producto

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val productoInfo = productosList[position].split(" - ")
                    val nombre: String? = productoInfo.getOrNull(0)
                    val nombreEmpresa: String? = productoInfo.getOrNull(1)
                    val ok: String? = "Este producto no contiene gluten"

                        onItemClickListener?.onItemClick(nombreEmpresa, nombre, ok)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(nombreEmpresa: String?, nombre: String?, ok: String?)
    }
}
