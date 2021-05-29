package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import kotlinx.android.synthetic.main.activity_count.*

class CountActivity : AppCompatActivity() {

    var data1 = arrayOf("10회", "20회", "30회")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count)

        var adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, data1)
        val sidejack = findViewById<Button>(R.id.sidejack)

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item)

        spinner.adapter = adapter1

        start.setOnClickListener({
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "start 시작하기";
            Log.d("start 시작하기", ClickState)
            startActivity(intent)
        })
    }

}
