package com.drawing.keywordpick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private TextInputEditText titleText;
    private TextInputEditText contentText;
    private DbHelper dbHelper;
    private Button button_save;
    private String id, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = DbHelper.getInst(this);
        titleText = (TextInputEditText) findViewById(R.id.title_edit_id);
        contentText = (TextInputEditText) findViewById(R.id.edit_id);

        button_save = (Button) findViewById(R.id.button_save);

        //id, 제목, 내용 가져오기
        Intent intent = getIntent();
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
                title = titleText.getText().toString();
                content = contentText.getText().toString();
                dbHelper.updateData(id, title, content);
                finish();
            }
        });

    }
}