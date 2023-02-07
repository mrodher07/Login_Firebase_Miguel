package com.example.login_firebase_miguel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import com.example.login_firebase_miguel.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegistro.setOnClickListener {
            envioRegistro()
        }
        binding.btnLogin.setOnClickListener {
            var gmail = binding.etEmailLogin.text.toString().trim()
            var clave = binding.etClaveLogin.text.toString().trim()
            iniciarSesion(gmail, clave)
        }
    }

    fun envioRegistro(){
        val enviar = Intent(this, UserActivity::class.java)
        startActivity(enviar)
    }

    fun iniciarSesion(email: String, clave:String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, clave
        ).addOnCompleteListener{
            if(it.isSuccessful){
                //abrirPerfil()
                Toast.makeText(this, "Se ha iniciado Sesion", Toast.LENGTH_SHORT).show()
            }else{
                //showErrorAlert()
            }
        }
    }
}