package com.adriantoral.practicaandroid.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adriantoral.practicaandroid.R
import com.adriantoral.practicaandroid.databinding.ActivityProductBinding
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private var idProducto: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.idProducto = intent.getStringExtra("idProducto")

        this.realizarPeticionJSON()
    }

    private fun realizarPeticionJSON() {
        Volley.newRequestQueue(applicationContext).add(
            JsonObjectRequest(
                "https://fakestoreapi.com/products/${this.idProducto}",
                {
                    binding.textViewTitle.text = it.getString("title")
                    binding.textViewPrice.text = "${it.getDouble("price").toString()} €"
                    binding.textViewDescription.text = it.getString("description")
                    binding.textViewCategory.text = it.getString("category")
                    Glide
                        .with(this)
                        .load(it.getString("image"))
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(binding.imageViewProduct)
                },
                {
                }
            )
        )
    }
}