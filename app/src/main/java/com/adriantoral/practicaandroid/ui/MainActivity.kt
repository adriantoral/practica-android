package com.adriantoral.practicaandroid.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.adriantoral.practicaandroid.databinding.ActivityMainBinding
import com.adriantoral.practicaandroid.modelos.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.instanciar()
        this.setListeners()
    }

    private fun instanciar() {
        this.firebaseAuth = FirebaseAuth.getInstance()
        this.usuario = Usuario("", "")
    }

    private fun setListeners() {
        binding.buttonSignup.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.buttonSignup.id -> {
//                val intent: Intent = Intent(applicationContext, SignupActivity::class.java)
//                startActivity(intent)
//                finish()
            }

            binding.buttonLogin.id -> {
                usuario.correo = binding.editUser.text.toString()
                usuario.password = binding.editPassword.text.toString()

                this.firebaseAuth.signInWithEmailAndPassword(usuario.correo, usuario.password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful)
                            return@addOnCompleteListener Snackbar
                                .make(
                                    v,
                                    "La cuenta no existe, quieres crearla?",
                                    Snackbar.LENGTH_LONG
                                )
                                .setAction("Crear cuenta") {
                                    this.firebaseAuth.createUserWithEmailAndPassword(
                                        usuario.correo,
                                        usuario.password
                                    )
                                        .addOnCompleteListener {
                                            if (!it.isSuccessful)
                                                return@addOnCompleteListener Snackbar
                                                    .make(
                                                        v,
                                                        "Error al crear la cuenta",
                                                        Snackbar.LENGTH_LONG
                                                    )
                                                    .show()

                                            Snackbar
                                                .make(
                                                    v,
                                                    "Cuenta creada correctamente",
                                                    Snackbar.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                }
                                .show()

                        Snackbar
                            .make(v, "Sesión iniciada correctamente", Snackbar.LENGTH_LONG)
                            .show()

//                        val intent: Intent = Intent(applicationContext, MainActivity::class.java)
//                        intent.putExtra("usuario", usuario)
//                        startActivity(intent)
//                        finish()
                    }
            }
        }
    }
}