package org.tensorflow.lite.examples.posenet


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainloginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainlogin)

        val btn_click = findViewById<Button>(R.id.btn_click_2)

        btn_click.setOnClickListener{
            val intent = Intent(this,LowerbodyActivity::class.java)
            startActivity(intent)
        }
    }
}



