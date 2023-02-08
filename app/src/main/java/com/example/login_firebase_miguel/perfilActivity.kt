package com.example.login_firebase_miguel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login_firebase_miguel.databinding.ActivityMainBinding
import com.example.login_firebase_miguel.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class perfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    var auth:FirebaseAuth = FirebaseAuth.getInstance()
    var user: FirebaseUser? = auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(user != null){
            setup()
        }

        binding.ivOpciones.setOnClickListener {
            startActivity(Intent(this, optionsActivity::class.java))
        }
    }

    fun setup(){
        var correo = binding.tvEmail
        var usuario = binding.tvUsuario
        var nacionalidad = binding.tvNacionalidad
        var edad = binding.tvAge

        val db = Firebase.firestore
        db.collection("user").document(user?.email.toString()).get().addOnSuccessListener{
            documento->
            val email: String? = documento.getString("email")
            val user: String? = documento.getString("usuario")
            val nacionality: String? = documento.getString("nacionalidad")
            val age: String? = documento.getString("edad")

            correo.text = correo.text.toString()+" "+user
            correo.text = email
            usuario.text = user
            nacionalidad.text = nacionality
            edad.text = age
        }
        binding.ivCerrar.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}