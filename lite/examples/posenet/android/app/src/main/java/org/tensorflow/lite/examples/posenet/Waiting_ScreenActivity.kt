package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log


class Waiting_ScreenActivity : AppCompatActivity() {

    private val WAITING_TIME: Long = 5000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_screen)

        var intent = getIntent();

        var exercise1 = intent.getStringExtra("exercise1")
        var exercise2 = intent.getStringExtra("exercise2")

        var count = intent.getStringExtra("count")


        Handler().postDelayed({
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("exercise1", exercise1)
            intent.putExtra("exercise2", exercise2)
            intent.putExtra("count", count)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }, WAITING_TIME)





    }
}