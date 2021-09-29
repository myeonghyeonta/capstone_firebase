package org.tensorflow.lite.examples.posenet


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_logout.*

import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import org.tensorflow.lite.examples.posenet.Extensions.toast

class MainloginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainlogin)

       // lateinit var textView : TextView //TODO


        val btn_click_1 = findViewById<Button>(R.id.btn_click_1)
        val btn_click_2 = findViewById<Button>(R.id.btn_click_2)
        val btn_click_3 = findViewById<Button>(R.id.btn_click_3)

        // textView = findViewById(R.id.text_name) as TextView //TODO

        btn_click_1.setOnClickListener {
            val intent = Intent(this, AbdominalActivity::class.java)
            //intent.putExtra("name", textView.text.toString()) //TODO
            startActivity(intent)
        }

        btn_click_2.setOnClickListener {
            val intent = Intent(this, UpperbodyActivity::class.java)
            startActivity(intent)
        }

        btn_click_3.setOnClickListener {
            val intent = Intent(this, LowerbodyActivity::class.java)
            startActivity(intent)
        }

        btnSignOut.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            toast("로그아웃")
            finish()
        }



    }
}