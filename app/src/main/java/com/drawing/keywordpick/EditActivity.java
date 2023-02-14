package com.drawing.keywordpick;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    private Button button_save, button_delete;
    private String id, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = DbHelper.getInst(this);
        titleText = (TextInputEditText) findViewById(R.id.title_edit_id);
        contentText = (TextInputEditText) findViewById(R.id.edit_id);

        button_save = (Button) findViewById(R.id.button_save);
        button_delete = (Button) findViewById(R.id.button_delete);

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
                    title = titleText.getText().toString();
                    content = contentText.getText().toString();
                    dbHelper.updateData(id, title, content);
                    finish();
                }
            });
            //삭제하기
            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHelper.deleteData(id);
                    finish();
                }
            });
        }else{ /* 추가 */
            //저장하기
            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title = titleText.getText().toString();
                    content = contentText.getText().toString();
                    dbHelper.insertData(title, content);
                    finish();
                }
            });
            //삭제하기
            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }


    }
}