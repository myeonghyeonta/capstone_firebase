package org.tensorflow.lite.examples.posenet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.activity_count.*

var number=0
var a=""
class CountActivity : AppCompatActivity() {

    var data1 = arrayOf("10회", "5회", "1회")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_count)
/*
        val sidejack = findViewById<Button>(R.id.sidejack)
*/
        //TODO
        var intent = getIntent();
        var exercise1 = intent.getStringExtra("exercise1")
        var exercise2 = intent.getStringExtra("exercise2")


        /*

        드롭다운 형식 사용

        val spinner = findViewById<Spinner>(R.id.spinner)
        var adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, data1)

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
        */


        start.setOnClickListener({
            //아무것도 입력하지 않았을때 탈출
            if (exer_count.getText().toString().isNullOrEmpty()){
                Toast.makeText(this@CountActivity, "아무것도 입력하지 않았습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var numb=Integer.parseInt(exer_count.getText().toString())

            if (numb>20){
                Toast.makeText(this@CountActivity, "숫자가 너무 큽니다 20회 아래로 정해주세요", Toast.LENGTH_SHORT).show()
            }
            else if(numb<=0){
                Toast.makeText(this@CountActivity, "0보다 큰값으로 정해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                number=numb
            val intent = Intent(this, Waiting_ScreenActivity::class.java)
                Log.d("운동 진입", ClickState)

            if (ClickState == "sidejack 운동") {

                intent.putExtra("exercise1", exercise1)
                intent.putExtra("count", number.toString())

            } else if (ClickState == "sidebend left 운동") {
                intent.putExtra("exercise1", exercise1)
                intent.putExtra("count", number.toString())
            } else if (ClickState == "sidebend right 운동") {
                intent.putExtra("exercise2", exercise2)
                intent.putExtra("count", number.toString())
            }
            else if (ClickState == "widesquat 운동") {
                intent.putExtra("exercise1", exercise1)
                intent.putExtra("count", number.toString())
            }

            startActivity(intent)
            }
        })



    }

}
