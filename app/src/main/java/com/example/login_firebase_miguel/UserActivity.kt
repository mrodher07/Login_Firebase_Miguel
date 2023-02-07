package com.example.login_firebase_miguel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login_firebase_miguel.databinding.ActivityUserBinding
import com.google.firebase.auth.FirebaseAuth

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
                //abrirPerfil()
                Toast.makeText(this, "Registracion correcta", Toast.LENGTH_SHORT).show()
            }else{
                //showErrorAlert()
            }

        }
    }


}