package com.example.quizapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapplication.databinding.ActivityQuizBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    lateinit var list : ArrayList<QuestionModel>

    private var count : Int = 0

    private var score : Int = 0

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.firestore.collection("quiz")
            .get().addOnCompleteListener { task->

                //binding.question.setVisible(true)

                if (task.isSuccessful){

                    binding.question.visibility = android.view.View.VISIBLE
                    binding.option1.visibility = android.view.View.VISIBLE
                    binding.option2.visibility = android.view.View.VISIBLE
                    binding.option3.visibility = android.view.View.VISIBLE
                    binding.option4.visibility = android.view.View.VISIBLE

                    binding.option1.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
                    binding.option2.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
                    binding.option3.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
                    binding.option4.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))

                    list.clear()
                    for (document in task.result!!)
                    {

                        var questionModel = document.toObject(QuestionModel::class.java)
                        list.add(questionModel!!)
                    }
                    binding.question.setText(list.get(0).question)
                    binding.option1.setText(list.get(0).option1)
                    binding.option2.setText(list.get(0).option2)
                    binding.option3.setText(list.get(0).option3)
                    binding.option4.setText(list.get(0).option4)
                }
                else {
                    // Handle the error here if the data retrieval is not successful
                    Toast.makeText(this@QuizActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }

            }

        list = ArrayList<QuestionModel>()

        binding.option1.setOnClickListener(){

            delayBeforeNextData(binding.option1.text.toString())
            //nextData(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener(){

            //nextData(binding.option2.text.toString())
            delayBeforeNextData(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener(){

            //nextData(binding.option3.text.toString())
            delayBeforeNextData(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener(){

            //nextData(binding.option4.text.toString())
            delayBeforeNextData(binding.option4.text.toString())
        }

    }

    private fun delayBeforeNextData(selectedOption: String) {
        // Delay for 1000 milliseconds (adjust as needed)
        when (list[count].ans) {

            binding.option1.text.toString() -> binding.option1.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            binding.option2.text.toString() -> binding.option2.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            binding.option3.text.toString() -> binding.option3.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            binding.option4.text.toString() -> binding.option4.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
        }
        handler.postDelayed({
            // Call nextData after the delay
            resetOptionButtonColors()
            nextData(selectedOption)

        }, 1000)
    }

    private fun resetOptionButtonColors() {
        binding.option1.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark)) // 0 means transparent, you can set it to the default color
        binding.option2.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
        binding.option3.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
        binding.option4.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))

    }


    private fun nextData(selectedOption: String) {

        if (count < list.size){
            if (list.get(count).ans.equals(selectedOption))
            {
                score ++
            }
        }
        else{
            resetOptionButtonColors()
        }
        count ++

        if (count >= list.size)
        {
//            Toast.makeText(this@QuizActivity,score.toString(),Toast.LENGTH_LONG).show()

            val intent = Intent(this,ScoreActivity::class.java)
            intent.putExtra("SCORE",score)
            startActivity(intent)
            finish()
        }
        else {

//            resetOptionButtonColors()
            binding.question.setText(list.get(count).question)
            binding.option1.setText(list.get(count).option1)
            binding.option2.setText(list.get(count).option2)
            binding.option3.setText(list.get(count).option3)
            binding.option4.setText(list.get(count).option4)

        }
    }
}