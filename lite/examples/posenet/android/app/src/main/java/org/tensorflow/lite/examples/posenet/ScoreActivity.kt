package org.tensorflow.lite.examples.posenet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_logout.*
import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import kotlinx.android.synthetic.main.activity_score.*
import kotlinx.android.synthetic.main.tfe_pn_activity_posenet.*
import org.tensorflow.lite.examples.posenet.Extensions.toast
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class ScoreActivity : AppCompatActivity() {
    private lateinit var textViewName : TextView
    private lateinit var textViewCount : TextView
    private lateinit var textViewGoodCount : TextView
    private lateinit var textViewNormalCount : TextView
    private lateinit var textViewBadCount : TextView
    private lateinit var textViewScore : TextView
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        var intent = getIntent();


        //날짜
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        val Date: String = simpleDateFormat.format(calendar.getTime())
        Log.d("time",Date)

        var exercise = ""



        textViewName = findViewById(R.id.text_name) as TextView
        textViewCount = findViewById(R.id.text_count) as TextView

        textViewGoodCount = findViewById(R.id.count_good) as TextView
        textViewNormalCount = findViewById(R.id.count_normal) as TextView
        textViewBadCount = findViewById(R.id.count_bad) as TextView
        textViewScore = findViewById(R.id.text_score) as TextView

        if (ClickState == "sidejack 운동")
        {
            textViewName.setText(intent.getStringExtra("exercise1"))
        }
        else if(ClickState == "sidebend left 운동")
        {
            textViewName.setText(intent.getStringExtra("exercise1"))
        }
        else if(ClickState=="sidebend right 운동")
        {
            textViewName.setText(intent.getStringExtra("exercise2"))
        }

        textViewCount.setText(intent.getStringExtra("count")+" 회")
        textViewGoodCount.setText(intent.getStringExtra("count_good")+"%")
        textViewNormalCount.setText(intent.getStringExtra("count_normal")+"%")
        textViewBadCount.setText(intent.getStringExtra("count_bad")+"%")
        textViewScore.setText(intent.getStringExtra("score")+"점")

        var count_good = intent.getStringExtra("count_good")
        var count_normal = intent.getStringExtra("count_normal")
        var count_bad = intent.getStringExtra("count_bad")
        var score = intent.getStringExtra("score")



        data class Score(
            var count_good: String? = null,
            var count_normal: String? = null,
            var count_bad: String? = null,
            var score: String? = null) :Serializable




        fun writeNewScore(count_good: String,count_normal: String,count_bad: String,score: String) {

            val score = Score(count_good, count_normal, count_bad, score)
            database = Firebase.database.reference
            FirebaseUtils.firebaseAuth.uid?.let {
                database.child("scores").child(it).child(Date).child(ClickState).setValue(score)
            }
        }





        btnSignOut.setOnClickListener({
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            toast("로그아웃")
            startActivity(intent)
            finish()
        })

        btnOk.setOnClickListener {
            val intent = Intent(this, MainloginActivity::class.java)
            startActivity(intent)
            writeNewScore(count_good,count_normal,count_bad,score)


            //데이터 읽기
            val scoreReference = FirebaseDatabase.getInstance().getReference("Score")

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    val post = dataSnapshot.getValue<Score>()
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                }
            }
            scoreReference.addValueEventListener(postListener)

            database.child("Score").child(Date).get().addOnSuccessListener {
                Log.i("firebase읽기", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase읽기", "Error getting data", it)
            }
        }



    }


}

