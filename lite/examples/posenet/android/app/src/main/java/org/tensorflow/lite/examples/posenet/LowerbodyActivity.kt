package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_logout.*
import org.tensorflow.lite.examples.posenet.Extensions.toast

var ClickState = ""


class LowerbodyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lowerbody)

        btnSignOut.setOnClickListener ({
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            toast("로그아웃")
            startActivity(intent)
            finish()
        })



        val sidejack_tutorial = findViewById<Button>(R.id.sidejack_tutorial)
        val sidejack = findViewById<Button>(R.id.sidejack)

        sidejack_tutorial.setOnClickListener({
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "sidejack 학습";
            Log.d("sidejack 학습", ClickState)
            startActivity(intent)
        })

        sidejack.setOnClickListener({
            val intent = Intent(this, CountActivity::class.java)
            /*ClickState = "sidejack 운동";
            Log.d("sidejack 운동", ClickState)*/
            startActivity(intent)
        })





    }
}