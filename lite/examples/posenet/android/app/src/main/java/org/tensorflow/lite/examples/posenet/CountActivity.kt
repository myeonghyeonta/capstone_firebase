package org.tensorflow.lite.examples.posenet
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Button
import kotlinx.android.synthetic.main.activity_count.*
import android.widget.Spinner
var number=0
var a=""
class CountActivity : AppCompatActivity() {

    var data1 = arrayOf("10회", "2회", "1회")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_count)
        val spinner = findViewById<Spinner>(R.id.spinner)
        var adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, data1)
/*
        val sidejack = findViewById<Button>(R.id.sidejack)
*/

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item)

        spinner.adapter = adapter1
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                a=data1[p2]
                //Toast.makeText(this@CountActivity,"$a",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        start.setOnClickListener({
            number=(a.replace("회","")).toInt()
            val intent = Intent(this, CameraActivity::class.java)
            ClickState = "sidebend left 운동";
            Log.d("sidebend left 운동", ClickState)
            startActivity(intent)
        })



    }

}
