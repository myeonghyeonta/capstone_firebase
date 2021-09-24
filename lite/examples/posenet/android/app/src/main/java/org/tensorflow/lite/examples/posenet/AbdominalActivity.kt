package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_logout.*
import org.tensorflow.lite.examples.posenet.Extensions.toast
import org.tensorflow.lite.examples.posenet.lib.ActionCount


class AbdominalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abdominal)

        lateinit var textView : TextView //1

        //TODO
       // var intent2 = getIntent();
        //var name = intent2.getStringExtra("name")


        btnSignOut.setOnClickListener ({
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            toast("로그아웃")
            startActivity(intent)
            finish()
        })




        val sidebend_left_tutorial = findViewById<Button>(R.id.sidebend_tutorial)
        val sidebend_left = findViewById<Button>(R.id.sidebend)


        textView = findViewById(R.id.text_name) as TextView //TODO1

        sidebend_left_tutorial.setOnClickListener({
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "sidebend left 학습";
            ActionCount=0;
            Log.d("sidebend left 학습", ClickState)
            startActivity(intent)
        })

        sidebend_left.setOnClickListener({
            val intent = Intent(this, CountActivity::class.java)
            intent.putExtra("name", textView.text.toString()) //TODO
            ClickState = "sidebend left 운동";
            Log.d("sidebend left 운동", ClickState)
            //intent.putExtra("name", name)
            startActivity(intent)
        })
    }
}