package com.adriantoral.practicaandroid.adaptadores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.adriantoral.practicaandroid.R
import com.adriantoral.practicaandroid.modelos.Producto
import com.adriantoral.practicaandroid.ui.ProductActivity
import com.bumptech.glide.Glide

class AdaptadorProducto(var context: Context) : RecyclerView.Adapter<AdaptadorProducto.Holder>() {
    private val lista: ArrayList<Producto> = ArrayList()

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        var imagen: ImageView = view.findViewById(R.id.imagenFila)
        var idFila = view.findViewById<TextView>(R.id.idFila)
        var titulo: TextView = view.findViewById(R.id.tituloFila)

        init {
            view.setOnClickListener {
                val intent = Intent(view.context, ProductActivity::class.java)
                intent.putExtra("idProducto", idFila.text)
                startActivity(view.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(this.context).inflate(R.layout.item_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val producto = this.lista[position]
        holder.titulo.text = producto.title
        holder.idFila.text = producto.id.toString()
        Glide
            .with(this.context)
            .load(producto.image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imagen)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    fun addProducto(producto: Producto) {
        this.lista.add(producto)
        this.notifyItemInserted(this.lista.size - 1)
    }
}