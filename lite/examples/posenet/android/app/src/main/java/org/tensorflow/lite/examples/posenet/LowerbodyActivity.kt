package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_logout.*
import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import kotlinx.android.synthetic.main.activity_lowerbody.*
import org.tensorflow.lite.examples.posenet.Extensions.toast
import org.tensorflow.lite.examples.posenet.lib.ActionCount
import org.tensorflow.lite.examples.posenet.lib.BadCount
import org.tensorflow.lite.examples.posenet.lib.GoodCount
import org.tensorflow.lite.examples.posenet.lib.NormalCount


class LowerbodyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lowerbody)

        lateinit var textView : TextView //1

        btnSignOut.setOnClickListener ({
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            toast("로그아웃")
            startActivity(intent)
            finish()
        })

        back.setOnClickListener ({
            val intent = Intent(this, MainloginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })



        val widesquat_tutorial = findViewById<Button>(R.id.widesquat_tutorial)
        val widesquat = findViewById<Button>(R.id.widesquat)

        textView = findViewById(R.id.text_exercise1) as TextView //TODO1

        widesquat_tutorial.setOnClickListener({
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "widesquat 학습";
            ActionCount=0;
            Log.d("widesquat 학습", ClickState)
            startActivity(intent)
        })

        widesquat.setOnClickListener({
            val intent = Intent(this, CountActivity::class.java)
            intent.putExtra("exercise1", textView.text.toString()) //TODO
            ClickState = "widesquat 운동";

            GoodCount =0.0
            NormalCount =0.0
            BadCount =0.0

            Log.d("widesquat 운동", ClickState)
            startActivity(intent)
        })





    }
}