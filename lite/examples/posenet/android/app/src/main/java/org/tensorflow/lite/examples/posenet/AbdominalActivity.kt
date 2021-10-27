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


class AbdominalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abdominal)

        lateinit var textView1 : TextView //1
        lateinit var textView2 : TextView //2


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


        back.setOnClickListener ({
            val intent = Intent(this, MainloginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })


        val sidebend_left_tutorial = findViewById<Button>(R.id.sidebend_left_tutorial)
        val sidebend_left = findViewById<Button>(R.id.sidebend_left)
        val sidebend_right_tutorial = findViewById<Button>(R.id.sidebend_right_tutorial)
        val sidebend_right = findViewById<Button>(R.id.sidebend_right)

        textView1 = findViewById(R.id.text_exercise1) as TextView //TODO1
        textView2 = findViewById(R.id.text_exercise2) as TextView//TODO1

        // 사이드밴드 왼쪽
        sidebend_left_tutorial.setOnClickListener({
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "sidebend left 학습";
            ActionCount=0;
            Log.d("sidebend left 학습", ClickState)
            startActivity(intent)
        })

        sidebend_left.setOnClickListener({
            val intent = Intent(this, CountActivity::class.java)
            intent.putExtra("exercise1", textView1.text.toString()) //TODO
            ClickState = "sidebend left 운동";

            GoodCount =0.0
            NormalCount =0.0
            BadCount =0.0

            Log.d("sidebend left 운동", ClickState)
            startActivity(intent)
        })

        // 사이드밴드 오른쪽
        sidebend_right_tutorial.setOnClickListener({
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "sidebend right 학습";
            ActionCount=0;
            Log.d("sidebend right 학습", ClickState)
            startActivity(intent)
        })

        sidebend_right.setOnClickListener({
            val intent = Intent(this, CountActivity::class.java)
            intent.putExtra("exercise2", textView2.text.toString()) //TODO
            ClickState = "sidebend right 운동";

            GoodCount =0.0
            NormalCount =0.0
            BadCount =0.0

            Log.d("sidebend right 운동", ClickState)
            //intent.putExtra("name", name)
            startActivity(intent)
        })
    }
}