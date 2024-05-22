package com.adriantoral.practicaandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adriantoral.practicaandroid.databinding.ActivityProductBinding
import com.adriantoral.practicaandroid.modelos.Producto
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private var idProducto: String? = null

    private lateinit var firebase_database: FirebaseDatabase
    private lateinit var productos_ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.idProducto = intent.getStringExtra("idProducto")

        this.instanciar()
        this.realizarPeticionJSON()
    }

    private fun instanciar() {
        this.firebase_database =
            FirebaseDatabase.getInstance("https://ato-utad2324-default-rtdb.europe-west1.firebasedatabase.app")
        this.productos_ref = this.firebase_database.getReference("/productos")
    }

    private fun realizarPeticionJSON() {
        this.productos_ref.child(this.idProducto!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val producto = snapshot.getValue(Producto::class.java)!!
                    binding.textViewTitle.text = producto.title
                    binding.textViewDescription.text = producto.description
                    binding.textViewPrice.text = producto.price.toString()
                    binding.textViewCategory.text = producto.category
                    Glide.with(this@ProductActivity)
                        .load(producto.image)
                        .into(binding.imageViewProduct)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}