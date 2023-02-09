package com.example.login_firebase_miguel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.login_firebase_miguel.databinding.ActivityOptionsBinding
import com.example.login_firebase_miguel.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class optionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        binding.btnRestablecer.setOnClickListener {
            restablecerClave()
        }
        binding.btnBorrar.setOnClickListener {
            borrarUsuario()
        }
        binding.btnUsuario.setOnClickListener {
            actualizarUser()
        }
        binding.btnNacionalidad.setOnClickListener {
            actualizarNacio()
        }
        binding.btnEdad.setOnClickListener {
            actualizarEdad()
        }
    }

    private fun restablecerClave() {
        auth.sendPasswordResetEmail(user.email.toString()).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this, this.resources.getString(R.string.msgRestablecer), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun borrarUsuario() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(this.resources.getString(R.string.borrarUser))
        builder.setMessage(this.resources.getString(R.string.msg_borraruser))
        builder.setPositiveButton("Ok") { dialog, which ->

            user.delete().addOnCompleteListener {
                val dialog = builder.create()
                if (it.isSuccessful) {

                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(
                        this,
                        this.resources.getString(R.string.msg_borraruser2),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        it.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
        }
        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(this, R.color.green))

            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        dialog.show()
    }

    fun actualizarUser() {

        var btnActualizarUser = binding.btnUsuario


        btnActualizarUser.setOnClickListener {
            var txtCambio: String = ""
            val builder = AlertDialog.Builder(this)
            builder.setTitle( this.resources.getString(R.string.actualizarUser))

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                // Asigna el texto ingresado en el EditText a la variable "textoIngresado"
                txtCambio = input.text.toString()
                val documentReference =
                    Firebase.firestore.collection("user").document(user.email.toString())
                documentReference.update("usuario", txtCambio)

                Toast.makeText(
                    this,
                    this.resources.getString(R.string.msg_actualizarUser),
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, perfilActivity::class.java))
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()


        }

    }

    fun actualizarNacio() {

        var btnActualizarNacio= binding.btnNacionalidad


        btnActualizarNacio.setOnClickListener {
            var txtCambio: String = ""
            val builder = AlertDialog.Builder(this)
            builder.setTitle( this.resources.getString(R.string.actualizarNacio))
            val input = EditText(this)
            builder.setView(input)
            builder.setPositiveButton("OK") { _, _ ->
                // Asigna el texto ingresado en el EditText a la variable "textoIngresado"
                txtCambio = input.text.toString()
                val documentReference =
                    Firebase.firestore.collection("user").document(user.email.toString())
                documentReference.update("nacionalidad", txtCambio)

                Toast.makeText(
                    this,
                    this.resources.getString(R.string.msg_actualizarNacio),
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, perfilActivity::class.java))
            }
            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }

    }

    fun actualizarEdad() {

        var btnActualizarEdad= binding.btnEdad


        btnActualizarEdad.setOnClickListener {
            var txtCambio: String = ""
            val builder = AlertDialog.Builder(this)
            builder.setTitle( this.resources.getString(R.string.actualizarEdad))

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                // Asigna el texto ingresado en el EditText a la variable "textoIngresado"
                txtCambio = input.text.toString()
                val documentReference =
                    Firebase.firestore.collection("user").document(user.email.toString())
                documentReference.update("edad", txtCambio)

                Toast.makeText(
                    this,
                    this.resources.getString(R.string.actualizarEdad),
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, perfilActivity::class.java))
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()


        }

    }
}