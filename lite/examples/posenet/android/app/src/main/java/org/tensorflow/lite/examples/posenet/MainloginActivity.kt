package org.tensorflow.lite.examples.posenet


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_logout.*

import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import kotlinx.android.synthetic.main.activity_mainlogin.*
import org.tensorflow.lite.examples.posenet.Extensions.toast

class MainloginActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainlogin)

        // lateinit var textView : TextView //TODO


        btn_navi.setOnClickListener {
            layout_drawer.openDrawer(GravityCompat.START) //START : left, END : Right 랑 같은 말
        }

        naviview.setNavigationItemSelectedListener(this) //네비게이션 메뉴 아이템에 클릭 속성 부

        val btn_click_1 = findViewById<Button>(R.id.btn_click_1)
        val btn_click_2 = findViewById<Button>(R.id.btn_click_2)
        val btn_click_3 = findViewById<Button>(R.id.btn_click_3)

        // textView = findViewById(R.id.text_name) as TextView //TODO

        btn_click_1.setOnClickListener {
            val intent = Intent(this, AbdominalActivity::class.java)
            //intent.putExtra("name", textView.text.toString()) //TODO
            startActivity(intent)
        }

        btn_click_2.setOnClickListener {
            val intent = Intent(this, UpperbodyActivity::class.java)

            startActivity(intent)
        }

        btn_click_3.setOnClickListener {
            val intent = Intent(this, LowerbodyActivity::class.java)
            startActivity(intent)
        }

        btnSignOut.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            toast("로그아웃")
            finish()
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { //네비게이션 메뉴 아이템 클릭 시 수
        when (item.itemId) {


            R.id.navi_list -> {
                startActivity(Intent(this, MainloginActivity::class.java))

            }


            R.id.chart -> {
                startActivity(Intent(this, GraphActivity::class.java))

            }
        }
        layout_drawer.closeDrawers() //네비게이션 뷰 닫기
        return false
    }

    override fun onBackPressed() { //백버튼 누를 시 수행하는 메소
        if (layout_drawer.isDrawerOpen(GravityCompat.START)) {
            layout_drawer.closeDrawers()
        } else {
            super.onBackPressed() //일반 백버튼 기능 실행(finish 역할)
        }
    }
}