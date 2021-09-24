package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_logout.*
import org.tensorflow.lite.examples.posenet.Extensions.toast
import org.tensorflow.lite.examples.posenet.lib.ActionCount
import org.tensorflow.lite.examples.posenet.lib.BadCount
import org.tensorflow.lite.examples.posenet.lib.GoodCount
import org.tensorflow.lite.examples.posenet.lib.NormalCount

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
            ActionCount=0;
            Log.d("sidejack 학습", ClickState)
            startActivity(intent)
        })

        sidejack.setOnClickListener({
            val intent = Intent(this, CountActivity::class.java)
            ClickState = "sidejack 운동";

            GoodCount =0
            NormalCount =0
            BadCount =0

            Log.d("sidejack 운동", ClickState)
            startActivity(intent)
        })





    }
}