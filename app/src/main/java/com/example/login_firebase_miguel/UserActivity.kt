package com.example.login_firebase_miguel

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.login_firebase_miguel.databinding.ActivityUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistro.setOnClickListener {
            var gmail = binding.etEmailRegistro.text.toString().trim()
            var clave = binding.etClaveRegistro.text.toString().trim()
            crearNuevoUsuario(gmail, clave)
        }
    }

    fun crearNuevoUsuario(email: String, clave: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email, clave
        ).addOnCompleteListener{

            if(it.isSuccessful){
                Toast.makeText(this, "Registracion correcta", Toast.LENGTH_SHORT).show()
                println("r "+email)
                writeNewUser(email)
                abrirPerfil()
            }else{
                //showErrorAlert()
            }

        }
    }

    fun abrirPerfil(){
        val enviar = Intent(this, perfilActivity::class.java)
        startActivity(enviar)
    }

    fun writeNewUser(email:String) {
        val db = Firebase.firestore

        val data = hashMapOf(
            "email" to email,
            "usuario" to "nouser",
            "nacionalidad" to "nonacionality",
            "edad" to "0"
        )

        db.collection("user").document(email)
            .set(data)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener{ e -> println(e.message)}
    }


}