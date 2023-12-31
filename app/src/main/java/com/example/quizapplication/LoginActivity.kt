package com.example.quizapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapplication.databinding.ActivityLogin2Binding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin2Binding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)

        setContentView(binding.root)



        binding.button.setOnClickListener() {

            Firebase.auth.createUserWithEmailAndPassword(binding.email.editText?.text.toString(),
                binding.password.editText?.text.toString()).addOnCompleteListener {

                    if (it.isSuccessful){

                        Toast.makeText(this,"User created.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this,QuizActivity::class.java)
                        startActivity(intent)
                        finish()


                    }else {

                        Toast.makeText(this,"User not created.", Toast.LENGTH_LONG).show()

                    }

            }

        }
    }


}