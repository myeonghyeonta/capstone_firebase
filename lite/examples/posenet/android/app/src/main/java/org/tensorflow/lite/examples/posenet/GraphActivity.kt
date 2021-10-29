package org.tensorflow.lite.examples.posenet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_mainlogin.*
import kotlinx.android.synthetic.main.activity_mainlogin.btn_navi
import kotlinx.android.synthetic.main.activity_mainlogin.layout_drawer
import kotlinx.android.synthetic.main.activity_mainlogin.naviview
import kotlin.collections.ArrayList

class GraphActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var database: DatabaseReference
    var calendarArray = Array(100, { item -> "" })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)



        btn_navi.setOnClickListener {
            layout_drawer.openDrawer(GravityCompat.START) //START : left, END : Right 랑 같은 말

        }

       naviview.setNavigationItemSelectedListener(this)



        var barChart: BarChart = findViewById(R.id.barChart) // barChart 생성

        var calcount: Long
        var exercount: Long
        var timecount: Long
        var valuec: String
        var countsum: Int

        var exerArray = Array(100, { item -> "" })
        var timeArray = Array(100, { item -> "" })
        var valueArray = Array(100, { item -> "" })

        var entries = ArrayList<BarEntry>()

        database = Firebase.database.reference
        FirebaseUtils.firebaseAuth.uid?.let {
            database.child("scores").child(FirebaseUtils.firebaseAuth.uid!!).get()
                .addOnSuccessListener {
                    var iterator1 = it.children.iterator() //key에 접근하기 위해서 이터레이터 사용
                    calcount = it.childrenCount //루프문 위한 갯수
                    for (i in 0 until calcount) {
                        countsum = 0 //갯수 합 0초기화
                        if (iterator1.hasNext() == true) { //다음 원소가 있으면 진행
                            calendarArray[i.toInt()] =
                                iterator1.next().key.toString() //키값을 받아와서 배열에 저장
                            Log.i("firebase읽기1", "Got value  ${calendarArray[i.toInt()]}")
                        }
                        var iterator2 =
                            it.child(calendarArray[i.toInt()]).children.iterator() //다음 자식노드 접근 똑같이 이터레이터 사용
                        exercount = it.child(calendarArray[i.toInt()]).childrenCount
                        for (j in 0 until exercount) { //루프문 위한 갯수
                            if (iterator2.hasNext() == true) {
                                exerArray[j.toInt()] = iterator2.next().key.toString()
                                Log.i("firebase읽기2", "Got value  ${exerArray[j.toInt()]}")
                            }
                            var iterator3 = it.child(calendarArray[i.toInt()])
                                .child(exerArray[j.toInt()]).children.iterator()
                            timecount = it.child(calendarArray[i.toInt()])
                                .child(exerArray[j.toInt()]).childrenCount
                            for (k in 0 until timecount) {
                                if (iterator3.hasNext() == true) {
                                    timeArray[k.toInt()] = iterator3.next().key.toString()
                                    Log.i("firebase읽기3", "Got value  ${timeArray[k.toInt()]}")
                                }
                                valuec =
                                    it.child(calendarArray[i.toInt()]).child(exerArray[j.toInt()])
                                        .child(timeArray[k.toInt()])
                                        .child("count").value.toString() //키값이 count인 값만 저장
                                Log.i("firebase읽기4", "Got value  ${valuec}")
                                countsum = countsum + valuec.toInt()
                            }
                        }
                        valueArray[i.toInt()] = countsum.toString()
                    }

                    for (l in 0 until calcount) { //데이터셋 만들기
                        entries.add(BarEntry(l.toFloat() + 1f, valueArray[l.toInt()].toFloat()))
                    }

                    var set = BarDataSet(entries, "DataSet") // 데이터셋 초기화
                    set.color =
                        ContextCompat.getColor(applicationContext!!, R.color.mint) // 바 그래프 색 설정

                    val dataSet: ArrayList<IBarDataSet> = ArrayList()
                    dataSet.add(set)
                    val data = BarData(dataSet)
                    data.barWidth = 0.3f //막대 너비 설정
                    barChart.run {
                        this.data = data //차트의 데이터를 data로 설정해줌.
                        setFitBars(true)
                        invalidate()
                    }
                }
                .addOnFailureListener {
                    Log.e("firebase읽기2", "Error getting data", it)
                }
        }
        barChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setMaxVisibleValueCount(100) // 최대 보이는 그래프 개수를 7개로 지정
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawBarShadow(false) //그래프의 그림자
            setDrawGridBackground(false)//격자구조 넣을건지
            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                axisMaximum = 101f //100 위치에 선을 그리기 위해 101f로 맥시멈값 설정
                axisMinimum = 0f // 최소값 0
                granularity = 50f // 50 단위마다 선을 그리려고 설정.
                setDrawLabels(true) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(true) //격자 라인 활용
                setDrawAxisLine(true) // 축 그리기 설정
                axisLineColor = ContextCompat.getColor(context, R.color.black) // 축 색깔 설정
                gridColor = ContextCompat.getColor(context, R.color.black) // 축 아닌 격자 색깔 설정
                textColor = ContextCompat.getColor(context, R.color.black) // 라벨 텍스트 컬러 설정
                textSize = 15f //라벨 텍스트 크기
            }
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(true) // 축 그림
                setDrawGridLines(false) // 격자
                textColor = ContextCompat.getColor(context, R.color.black) //라벨 색상
                textSize = 10f // 텍스트 크기
                valueFormatter = MyXAxisFormatter() // X축 라벨값(밑에 표시되는 글자) 바꿔주기 위해 설정
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }

    }

    inner class MyXAxisFormatter : ValueFormatter() {
        private val days = calendarArray
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
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

