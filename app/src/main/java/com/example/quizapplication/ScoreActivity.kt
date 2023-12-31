package com.example.quizapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapplication.databinding.ActivityScoreBinding
import com.google.firebase.auth.FirebaseAuth

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.score.setText("Congrats !!! Your Score is ${intent.getIntExtra("SCORE",0)}")
        binding.logout.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))

        binding.logout.setOnClickListener(){

            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this@ScoreActivity,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}