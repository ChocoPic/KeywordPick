package com.drawing.keywordpick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
            dbHelper.insertData("목록1","토끼\n고양이\n시계\n여자아이");
            pref.edit().putBoolean("isFirstRun",false).apply();
        }
    }
}
