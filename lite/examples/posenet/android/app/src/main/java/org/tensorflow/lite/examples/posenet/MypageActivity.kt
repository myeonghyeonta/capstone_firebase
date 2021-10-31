package org.tensorflow.lite.examples.posenet

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.graphics.Color
import android.icu.util.*
import android.util.Log
import android.view.MenuItem

import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.widget.TextView
import android.widget.CalendarView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_mainlogin.btn_navi
import kotlinx.android.synthetic.main.activity_mainlogin.layout_drawer
import kotlinx.android.synthetic.main.activity_mainlogin.naviview
import kotlinx.android.synthetic.main.activity_count.*
import kotlinx.android.synthetic.main.activity_logout.*
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_logout.btnSignOut
import org.tensorflow.lite.examples.posenet.Extensions.toast
import java.lang.Exception

class MypageActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var database: DatabaseReference
    lateinit var calendarView: CalendarView
    lateinit var textView: TextView

    val colorItems=ArrayList<Int>()
    lateinit var pieDataSet : PieDataSet
    lateinit var pieData : PieData
    val testdata=false
    var calendar_date_set=ArrayList<String>()
    //파이차트에 들어갈 데이터를 저장할 변수
    //pie_data[0] = "sidebend left 운동" ,
    //pie_data[1] = "sidebend right 운동" ,
    //pie_data[2] = "sidejack 운동" ,
    //pie_data[3] = "widesquat 운동" ,
    var pie_data=Array(4, {item -> 0.0f})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        database = Firebase.database.reference
        //처음에 있는 데이터들 다 불러와서 정리
        var calcount: Long
        var founded_string=""
        FirebaseUtils.firebaseAuth.uid?.let {
            database.child("scores").child(FirebaseUtils.firebaseAuth.uid!!).get()
                .addOnSuccessListener {
                    var iterator1 = it.children.iterator() //key에 접근하기 위해서 이터레이터 사용
                    calcount = it.childrenCount
                    for (i in 0 until calcount) {
                        founded_string=""
                        if (iterator1.hasNext() == true) {
                            founded_string=iterator1.next().key.toString()
                            calendar_date_set.add(founded_string)
                            Log.i("firebase읽기1", "Got value  ${calendar_date_set[i.toInt()]}")
                        }
                        var iterator2 =
                            it.child(founded_string).children.iterator() //다음 자식노드 접근 똑같이 이터레이터 사용
                        //Log.i("firebase읽기2", "Got value  ${}")
                    }
                }
        }

        //calendar_date_set.contains("넷") 로 안에 있는지 여부확인가능

        setContentView(R.layout.activity_mypage)

        btn_navi.setOnClickListener {
            layout_drawer.openDrawer(GravityCompat.START) //START : left, END : Right 랑 같은 말

        }

        btnSignOut.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            toast("로그아웃")
            finish()
        }

        naviview.setNavigationItemSelectedListener(this)

        Piechart.isVisible=false
        //check_day(2021,9,26)
        calendarView=findViewById((R.id.calendarView))
        textView=findViewById((R.id.diaryTextView))

        //데이터 추가하는 과정
        //위에서 알아서 퍼센트로 변경해주기 때문에  value에
        //운동횟수만 넣어주면 됨

        //색을 리스트에 추가
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorItems.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colorItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorItems.add(c)
        colorItems.add(ColorTemplate.getHoloBlue())

        calendarView.setOnDateChangeListener {view, year, month, dayOfMonth ->
            Piechart.isVisible=false
            check_day(year, month+1, dayOfMonth)
        }


        diarybutton.setOnClickListener(){
            val entries = ArrayList<PieEntry>()
            Piechart.isVisible=true
            Piechart.setUsePercentValues(true)
            Piechart.setEntryLabelTextSize(0f) // 파이차트 글씨 안나오게
            entries.add(PieEntry(pie_data[0],"sidebend left"))
            entries.add(PieEntry(pie_data[1],"sidebend right"))
            entries.add(PieEntry(pie_data[2],"sidejack"))
            entries.add(PieEntry(pie_data[3],"widesquat"))
            //그래프에 사용할 색을 리스트에서 꺼내옴
            //그리고 그래프에서 각요소의 표시 글자 구성을 설정
            pieDataSet=PieDataSet(entries,"")
            pieDataSet.apply{
                colors=colorItems
                valueTextColor= Color.BLACK
                valueTextSize=16f



            }

            //추가한 데이터를  PieData 형태로 추가
            pieData= PieData(pieDataSet)
            Piechart.apply{
                data=pieData
                description.isEnabled=false
                isRotationEnabled=false
                setEntryLabelColor(Color.BLACK)
                animateY(1400, Easing.EaseInOutQuad)
                animate()
            }

        }


    }

    //날짜를 주면, 운동기록이 있으면 버튼을 보이게.
    //없으면 그냥 텍스트만 보이게 하는 함수
    fun check_day(year:Int, month:Int, day:Int){
        try{
            //선택한 날짜를 firebase에 저장된 날짜와 형식을 맞춘다
                var checking_date =year.toString()+"-"+month.toString()+"-"+day.toString()
            Log.i("firebase읽기, ch", "Got value  ${checking_date}")
            //운동기록이 있는 경우
            //if 안에 운동기록 찾는 과정을 넣거나 먼저 실행해주기

            //데이터 배열 초기화
            for ( j in 0 until 4){
                pie_data[j.toInt()]=0.0f
            }
            if(calendar_date_set.contains(checking_date)){
                diaryTextView.isVisible=false
                diarybutton.isVisible=true
                FirebaseUtils.firebaseAuth.uid?.let {
                    database.child("scores").child(FirebaseUtils.firebaseAuth.uid!!).get()
                        .addOnSuccessListener {
                            var time_iter=it.child(checking_date).children.iterator()
                            var exer_count=it.child(checking_date).childrenCount
                            var exer_index=0
                            for (i in 0 until exer_count) {
                                var now_exer=""
                                if (time_iter.hasNext() == true){
                                    //해당 날짜에 존재하는 운동목록에 따라 분기
                                    now_exer=time_iter.next().key.toString()
                                    if (now_exer=="sidebend left 운동"){
                                        exer_index=0
                                    }
                                    else if (now_exer=="sidebend right 운동"){
                                        exer_index=1
                                    }
                                    else if (now_exer=="sidejack 운동"){
                                        exer_index=2
                                    }
                                    else if (now_exer=="widesquat 운동"){
                                        exer_index=3
                                    }
                                }
                                //score -> date(ex: 2021-10-29) -> exer(ex: sidejack 운동) ->exer_date( ex 11:02:00 )를 의미
                                var exer_iter = it.child(checking_date).child(now_exer).children.iterator()
                                var timecount = it.child(checking_date).child(now_exer).childrenCount
                                for (k in 0 until timecount) {
                                    var now_time=""
                                    if (exer_iter.hasNext()==true){
                                        now_time=exer_iter.next().key.toString()
                                        Log.i("firebase읽기3", "Got value  ${now_time}")
                                    }
                                    var valuec=it.child(checking_date).child(now_exer).child(now_time).child("count").value.toString()
                                    Log.i("firebase읽기4", "Got value  ${valuec}")
                                    pie_data[exer_index.toInt()]= pie_data[exer_index.toInt()] + valuec.toFloat()
                                }

                            }

                        }
                }
            }
            else {
                diaryTextView.isVisible=true
                diarybutton.isVisible=false
                diaryTextView.text = String.format("%d / %d / %d 없음", year, month , day)
                //diarybutton.text = String.format("%d / %d / %d", year, month + 1, day)
            }
        }
        catch (e:Exception){

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
