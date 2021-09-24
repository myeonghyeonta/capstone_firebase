package org.tensorflow.lite.examples.posenet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_logout.*
import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import kotlinx.android.synthetic.main.activity_score.*
import org.tensorflow.lite.examples.posenet.Extensions.toast

class ScoreActivity : AppCompatActivity() {
    private lateinit var textViewName : TextView
    private lateinit var textViewCount : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        var intent = getIntent();

        textViewName = findViewById(R.id.text_namee) as TextView
        textViewCount = findViewById(R.id.text_count) as TextView
        textViewName.setText(intent.getStringExtra("exercise1"))
        textViewCount.setText(intent.getStringExtra("count")+" 회")

        btnSignOut.setOnClickListener({
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            toast("로그아웃")
            startActivity(intent)
            finish()
        })

        btnOk.setOnClickListener {
            val intent = Intent(this, MainloginActivity::class.java)
            startActivity(intent)
        }
    }
}

