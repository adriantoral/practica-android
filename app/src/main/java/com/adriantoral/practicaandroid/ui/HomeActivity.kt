package com.adriantoral.practicaandroid.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adriantoral.practicaandroid.adaptadores.AdaptadorProducto
import com.adriantoral.practicaandroid.databinding.ActivityHomeBinding
import com.adriantoral.practicaandroid.modelos.Producto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var firebase_database: FirebaseDatabase
    private lateinit var productos_ref: DatabaseReference
    private lateinit var adaptadorProducto: AdaptadorProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.instanciar()
        this.createRecycler()
        this.realizarPeticionArrayJSON()
    }

    private fun instanciar() {
        this.adaptadorProducto = AdaptadorProducto(this)
        this.firebase_database =
            FirebaseDatabase.getInstance("https://ato-utad2324-default-rtdb.europe-west1.firebasedatabase.app")
        this.productos_ref = this.firebase_database.getReference("/productos")
    }

    private fun realizarPeticionArrayJSON() {
        this.productos_ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adaptadorProducto.clear()
                for (producto in snapshot.children) {
                    val producto_data = producto.getValue(Producto::class.java)
                    producto_data?.firebase_id = producto.key.toString()
                    adaptadorProducto.addProducto(producto_data!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun createRecycler() {
        binding.recyclerModelos.adapter = adaptadorProducto
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            binding.recyclerModelos.layoutManager = GridLayoutManager(this, 2)
        else binding.recyclerModelos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}