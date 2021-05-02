package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_logout.*
import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import kotlinx.android.synthetic.main.activity_mainlogin.*
import org.tensorflow.lite.examples.posenet.Extensions.toast

class LowerbodyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lowerbody)

        btnSignOut.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            toast("로그아웃")
            finish()
        }




        val btn_back =findViewById<ImageButton>(R.id.back)
        val learning = findViewById<Button>(R.id.learning)
        val squat = findViewById<Button>(R.id.squat)

        btn_back.setOnClickListener{
            val intent = Intent(this,MainloginActivity::class.java)
            startActivity(intent)
        }

        learning.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "squat 학습";
            Log.d("squat 학습", ClickState)
            startActivity(intent)
        }

        squat.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "squat 운동";
            Log.d("squat 운동", ClickState)
            startActivity(intent)
        }
    }
}