package com.moviedb.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.moviedb.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener(this)

    }

    private fun loginFirebase(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                    val currentUser = auth.currentUser
                    Toast.makeText(this, "Bienvenido(a) ${currentUser?.email}.", Toast.LENGTH_SHORT,).show()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT,).show()
                }
            }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnLogin ->{
                if (binding.edtEmail.text.toString().trim().isNotEmpty() &&
                    binding.edtPassword.text.toString().trim().isNotEmpty()){
                    loginFirebase(binding.edtEmail.text.toString().trim(),binding.edtPassword.text.toString().trim())
                }else{
                    Toast.makeText(this,"Ingrese los datos solicitados",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}