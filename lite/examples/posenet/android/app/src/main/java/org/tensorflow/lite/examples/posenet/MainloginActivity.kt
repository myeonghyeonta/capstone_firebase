package org.tensorflow.lite.examples.posenet


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_logout.*
import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import kotlinx.android.synthetic.main.activity_mainlogin.*
import org.tensorflow.lite.examples.posenet.Extensions.toast

class MainloginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainlogin)

        val btn_click1 = findViewById<Button>(R.id.btn_click_1)
        val btn_click2 = findViewById<Button>(R.id.btn_click_2)

        btn_click_1.setOnClickListener{
            val intent = Intent(this,AbdominalActivity::class.java)
            startActivity(intent)
        }

        btn_click_2.setOnClickListener{
            val intent = Intent(this,LowerbodyActivity::class.java)
            startActivity(intent)
        }

        btnSignOut.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            toast("로그아웃")
            finish()
        }
    }
}



