package com.drawing.keywordpick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

//TODO: 예외처리, 디자인 수정
//TODO: outline button 색 왜이래
//TODO: 테마 전체적으로 수정할 것

public class BottomBarActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    PickActivityFragment menu1 = new PickActivityFragment();
    ListActivityFragment menu2 = new ListActivityFragment();

    private DbHelper dbHelper;
    private SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        // DB 세팅
        dbHelper = new DbHelper(this);

        // 첫실행시
        pref = getSharedPreferences("first",MODE_PRIVATE);
        checkFirstRun();

        // 프래그먼트 세팅
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout, menu1);   //첫화면
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // 하단 바 세팅
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(BottomBarActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                changeFragment(item.getItemId());
                return true;
            }
        });
    }

    //화면 전환 함수
    public void changeFragment(int id){
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (id){
            case R.id.menu1:
                fragmentTransaction.replace(R.id.framelayout, menu1);   //프래그먼트 교체
                fragmentTransaction.commit();
                break;
            case R.id.menu2:
                fragmentTransaction.replace(R.id.framelayout, menu2);   //프래그먼트 교체
                fragmentTransaction.commit();
                break;
        }
    }

    //첫실행 확인 함수
    private void checkFirstRun(){
        boolean isFirstRun = pref.getBoolean("isFirstRun", true);
        if(isFirstRun){
            dbHelper.insertData("기본목록","TV\n" +
                    "간호사\n" +
                    "갈매기\n" +
                    "개\n" +
                    "거미줄\n" +
                    "검사\n" +
                    "고양이\n" +
                    "곰\n" +
                    "곰인형\n" +
                    "공원\n" +
                    "공주\n" +
                    "과거\n" +
                    "과자\n" +
                    "교복\n" +
                    "교사\n" +
                    "구름\n" +
                    "국서\n" +
                    "귀신\n" +
                    "귀여움\n" +
                    "귀찮음\n" +
                    "그림쟁이\n" +
                    "기쁨\n" +
                    "꽃\n" +
                    "꿈\n" +
                    "나무\n" +
                    "나비\n" +
                    "낙엽\n" +
                    "냉정함\n" +
                    "냉철함\n" +
                    "네일아트\n" +
                    "네잎클로버\n" +
                    "노래\n" +
                    "노트\n" +
                    "눈\n" +
                    "다람쥐\n" +
                    "달\n" +
                    "달력\n" +
                    "담요\n" +
                    "대통령\n" +
                    "도련님\n" +
                    "도마뱀\n" +
                    "도서관\n" +
                    "동전\n" +
                    "뜨개질\n" +
                    "리본\n" +
                    "립스틱\n" +
                    "마법\n" +
                    "마카롱\n" +
                    "망토\n" +
                    "멧돼지\n" +
                    "멸망\n" +
                    "모래시계\n" +
                    "무서움\n" +
                    "문화재\n" +
                    "물\n" +
                    "물고기\n" +
                    "미래\n" +
                    "미로\n" +
                    "미소\n" +
                    "민들레\n" +
                    "바다\n" +
                    "바람\n" +
                    "밤\n" +
                    "방패\n" +
                    "백수\n" +
                    "변호사\n" +
                    "별\n" +
                    "병원\n" +
                    "보조배터리\n" +
                    "불\n" +
                    "붓\n" +
                    "비\n" +
                    "비둘기\n" +
                    "빗자루\n" +
                    "사과\n" +
                    "사랑\n" +
                    "산\n" +
                    "샌드위치\n" +
                    "서재\n" +
                    "선물\n" +
                    "설레임\n" +
                    "성스러움\n" +
                    "솜사탕\n" +
                    "스탠드\n" +
                    "스피커\n" +
                    "슬픔\n" +
                    "시간\n" +
                    "시녀\n" +
                    "아가씨\n" +
                    "아싸\n" +
                    "악함\n" +
                    "얼음\n" +
                    "여왕\n" +
                    "여행\n" +
                    "연필\n" +
                    "염동력\n" +
                    "오렌지\n" +
                    "왕\n" +
                    "왕비\n" +
                    "왕자\n" +
                    "용\n" +
                    "우주\n" +
                    "위로\n" +
                    "유능함\n" +
                    "유령\n" +
                    "의사\n" +
                    "이어폰\n" +
                    "인싸\n" +
                    "자전거\n" +
                    "잔디\n" +
                    "장갑\n" +
                    "장미\n" +
                    "저택\n" +
                    "전기\n" +
                    "정장\n" +
                    "젤리\n" +
                    "주방\n" +
                    "주사위\n" +
                    "지갑\n" +
                    "지폐\n" +
                    "집사\n" +
                    "짜증남\n" +
                    "참새\n" +
                    "책\n" +
                    "총\n" +
                    "춤\n" +
                    "친절함\n" +
                    "카네이션\n" +
                    "칼\n" +
                    "컴퓨터\n" +
                    "코딩\n" +
                    "코스모스\n" +
                    "태블릿\n" +
                    "토끼\n" +
                    "튤립\n" +
                    "퍼즐\n" +
                    "폐허\n" +
                    "포도\n" +
                    "폰\n" +
                    "풍선\n" +
                    "풍선껌\n" +
                    "하늘\n" +
                    "학교\n" +
                    "학생\n" +
                    "한복\n" +
                    "해커\n" +
                    "행복\n" +
                    "호랑이\n" +
                    "화남\n" +
                    "화분\n");
            pref.edit().putBoolean("isFirstRun",false).apply();
        }
    }
}
