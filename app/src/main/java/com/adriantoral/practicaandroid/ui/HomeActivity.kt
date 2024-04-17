package com.adriantoral.practicaandroid.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adriantoral.practicaandroid.adaptadores.AdaptadorProducto
import com.adriantoral.practicaandroid.databinding.ActivityHomeBinding
import com.adriantoral.practicaandroid.modelos.Producto
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

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
    }

    private fun realizarPeticionArrayJSON() {
        Volley.newRequestQueue(applicationContext).add(
            JsonArrayRequest(
                "https://fakestoreapi.com/products",
                {
                    for (i in 0..<it.length()) {
                        this.adaptadorProducto.addProducto(
                            Gson().fromJson(
                                it.getJSONObject(i).toString(), Producto::class.java
                            )
                        )
                    }
                },
                {
                }
            )
        )
    }

    private fun createRecycler() {
        binding.recyclerModelos.adapter = adaptadorProducto
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            binding.recyclerModelos.layoutManager = GridLayoutManager(this, 2)
        else binding.recyclerModelos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}