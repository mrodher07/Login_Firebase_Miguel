package com.example.login_firebase_miguel

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Toast
import com.example.login_firebase_miguel.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegistro.setOnClickListener {
            envioRegistro()
        }
        binding.btnLogin.setOnClickListener {
            var gmail = binding.etEmailLogin.text.toString().trim()
            var clave = binding.etClaveLogin.text.toString().trim()
            iniciarSesion(gmail, clave)
        }
        binding.ivLoginGoogle.setOnClickListener{
            iniciarSesionGoogle()
        }
    }

    companion object{
        private const val RC_SIGN_IN = 423
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
                abrirPerfil()
            }else{
                Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                //showErrorAlert()
            }
        }
    }

    fun iniciarSesionGoogle(){
        val providerGoogle = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providerGoogle).build(),
        Companion.RC_SIGN_IN)
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
            .addOnFailureListener{ e -> Log.w(ContentValues.TAG, "Error writing document", e)}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Companion.RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                writeNewUser(user?.email.toString())
                val intent = Intent(this, perfilActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart(){
        super.onStart()
        if(auth.currentUser!=null){
            val intent = Intent(this, perfilActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun abrirPerfil(){
        val enviar = Intent(this, perfilActivity::class.java)
        startActivity(enviar)
    }
}