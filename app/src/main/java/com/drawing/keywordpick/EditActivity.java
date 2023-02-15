package com.drawing.keywordpick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                    title = titleText.getText().toString();
                    content = contentText.getText().toString();
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
    }
    private Boolean isNull(String text){
        if(text==null || text.length()==0 || text.replace(" ","").equals("")){
            return true;
        }else{
            return false;
        }
    }

}