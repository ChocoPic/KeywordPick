package com.drawing.keywordpick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    private TextInputEditText titleText;
    private TextInputEditText contentText;
    private DbHelper dbHelper;
    private Button button_save, button_delete;
    private Button btnBack;
    private String id, title, content;
    private LinearLayout adContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // 배너광고 로드
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus){}
        });
        adContainerView = findViewById(R.id.adContainer2);
        loadBanner();

        dbHelper = DbHelper.getInst(this);
        titleText = (TextInputEditText) findViewById(R.id.title_edit_id);
        contentText = (TextInputEditText) findViewById(R.id.edit_id);

        button_save = (Button) findViewById(R.id.button_save);
        button_delete = (Button) findViewById(R.id.button_delete);
        btnBack = findViewById(R.id.btn_back);

        Intent intent = getIntent();
        /* 수정 */
        // id, 제목, 내용 가져오기
        if(!TextUtils.isEmpty(intent.getStringExtra("title"))){
            title = intent.getStringExtra("title");
            titleText.setText(title);
            List<MyData> temp = new ArrayList<>();
            temp = dbHelper.getData(title);
            content = temp.get(0).content;
            id = temp.get(0).id;
            contentText.setText(content);
            //저장하기
            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title = Objects.requireNonNull(titleText.getText()).toString();
                    content = Objects.requireNonNull(contentText.getText()).toString();
                    if(isNull(title) || isNull(content)){
                        Toast.makeText(getApplicationContext(),"내용을 입력하세요",Toast.LENGTH_LONG).show();
                    }else{
                        dbHelper.updateData(id, title, content);
                        finish();
                    }
                }
            });
            //삭제하기
            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                    builder.setMessage("삭제할까요?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbHelper.deleteData(id);
                            finish();
                        }
                    });
                    builder.setNegativeButton("아니오",null);
                    builder.create().show();
                }
            });
        }
        /* 추가 */
        else{
            //저장하기
            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title = Objects.requireNonNull(titleText.getText()).toString();
                    content = Objects.requireNonNull(contentText.getText()).toString();
                    if(isNull(title) || isNull(content)){
                        Toast.makeText(getApplicationContext(),"내용을 입력하세요",Toast.LENGTH_LONG).show();
                    }else{
                        dbHelper.insertData(title, content);
                        finish();
                    }
                }
            });
            //삭제하기
            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                    builder.setMessage("삭제할까요?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("아니오", null);
                    builder.create().show();
                }
            });
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private Boolean isNull(String text){
        if(text==null || text.isEmpty() || text.replace(" ", "").isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void loadBanner() {

        // Create a new ad view.
        AdView adView = new AdView(this);
        adView.setAdSize(getAdSize());
//        adView.setAdUnitId("ca-app-pub-3940256099942544/9214589741"); //테스트
        adView.setAdUnitId("ca-app-pub-9932148089014412/6848245614");
        // Replace ad container with new ad view.
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        // Start loading the ad in the background.
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}